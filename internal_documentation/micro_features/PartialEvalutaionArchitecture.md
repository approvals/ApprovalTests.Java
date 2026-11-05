# Partial Evaluation Architecture

How the `approvaltests-partial` tool is put together. For what it does and why, see [PartialEvalutaion.md](PartialEvalutaion.md).

All code lives in `approvaltests-partial/src/main/java/org/approvaltests/partial/`.

## Components

```mermaid

flowchart LR
    user(["Your project<br/>approvaltests-selection.json"])
    script["CreatePartailApprovals.sh<br/>compiles the tool, then runs it"]
    cli["PartialEvaluationCli<br/>build / explain, --current"]

    subgraph tool["approvaltests-partial"]
        config["SelectionConfig<br/>reads the JSON"]
        checkout["SourceCheckout<br/>clone, or fetch + reset"]
        jars["JarBuilder<br/>mvn package, merge jars,<br/>third-party classpath, stats"]
        shaker["TreeShaker<br/>mark + sweep on the source"]
        shake["ShakeReport<br/>what was removed,<br/>why each class was kept"]
        html["HtmlReport<br/>before/after HTML report"]
        shell["Shell<br/>runs git / mvn"]
    end

    github[("GitHub<br/>approvals/ApprovalTests.Java")]
    local[(".local/approvaltests-&lt;version&gt;<br/>or .local/approvaltests-master")]
    out(["target/approvaltests-custom/<br/>approvaltests-&lt;version&gt;.jar<br/>approvaltests-&lt;version&gt;-report.html"])

    user --> script --> cli
    cli --> config
    cli --> checkout
    cli --> jars
    cli --> shaker
    cli --> html
    shaker --> shake
    checkout --> shell
    jars --> shell
    shell --> github
    checkout --> local
    shaker --> local
    jars --> local
    jars --> out
    html --> out

```

| Class | Responsibility |
|---|---|
| `PartialEvaluationCli` | Parses `build` / `explain` / `--current` / `--config`, and runs the pipeline. |
| `SelectionConfig` | The `approvaltests-selection.json` model (version, entry points, runtime discoveries, output). |
| `SourceCheckout` | Gets the source: shallow-clone a tag or `master`, or fetch and `reset --hard` an existing checkout. |
| `JarBuilder` | Runs Maven on `approvaltests-util` + `approvaltests`, writes their third-party classpath, merges the two jars into one, and measures jars. |
| `TreeShaker` | Parses the source with JavaParser + symbol solver, marks what is reachable, and deletes the rest in place. |
| `ShakeReport` | Results of a shake: deleted files and directories, removed methods and types, and the "kept because" chain for every kept class. |
| `HtmlReport` | Compares the original source snapshot with the shaken checkout, and writes the HTML report. |
| `Shell` | Runs `git` and `mvn` processes. |

## Build Pipeline

```mermaid
sequenceDiagram
    autonumber
    participant CLI as PartialEvaluationCli
    participant SC as SourceCheckout
    participant JB as JarBuilder
    participant HR as HtmlReport
    participant TS as TreeShaker
    participant L as .local checkout
    participant P as Your project

    CLI->>SC: cloneTag(version) or cloneBranch("master") with --current
    SC->>L: clone, or fetch + checkout + reset --hard + clean
    CLI->>JB: build(checkout)
    JB->>L: mvn clean package dependency:build-classpath
    JB-->>CLI: original jars → baseline Stats
    CLI->>HR: snapshot(checkout)
    HR-->>CLI: original sources
    CLI->>TS: shake(checkout, entryPoints, thirdPartyJars)
    TS->>L: delete / rewrite .java files, delete empty directories
    TS-->>CLI: ShakeReport
    CLI->>JB: build(checkout) again
    JB->>L: mvn clean package (tests skipped)
    CLI->>JB: merge(jars)
    JB->>P: approvaltests-<version>.jar
    CLI->>HR: write(inputs)
    HR->>L: git diff (for the per-file diffs)
    HR->>P: approvaltests-<version>-report.html
```

`explain` runs steps 1–2, builds only if the third-party classpath is missing, shakes, and prints `ShakeReport.keptBecause` for classes matching the prefix. It does not build the partial jar.

## Tree Shaking

```mermaid
flowchart TD
    start(["shake(checkout, entryPoints, thirdPartyJars)"])
    parse["Parse every .java in<br/>approvaltests-util and approvaltests<br/>(JavaParser + symbol solver with JDK,<br/>both source roots, third-party jars)"]
    index["Index types by name,<br/>methods by signature and by name"]
    roots["Mark entry points<br/>(methods, or the type for a field)"]

    subgraph mark["Mark (worklist until nothing new)"]
        drain["Scan each kept method / type"]
        refs["Type names → keep type<br/>(and its fields, initializers, constructors)"]
        strings["String literal = our class name → keep type"]
        calls["Method calls → resolve with symbol solver"]
        resolved{"Resolved?"}
        exact["Keep that method<br/>(all same-arity overloads if a lambda is passed)"]
        byname["Keep by name + arity,<br/>limited to the named class;<br/>nothing if the class isn't ours"]
        virtual["Virtual rules for kept types:<br/>abstract, @Override, implements inherited abstract,<br/>name called somewhere, third-party supertype"]
    end

    drop["Drop types kept only by a string<br/>that end up empty"]
    explain["Record 'kept because' chains"]

    subgraph sweep["Sweep"]
        files["Delete files with no kept types"]
        members["Delete unreached methods and nested types"]
        imports["Remove dead and unused imports"]
        write["Write files (formatting preserved)"]
        dirs["Delete empty directories"]
    end

    start --> parse --> index --> roots --> drain
    drain --> refs --> drain
    drain --> strings --> drain
    drain --> calls --> resolved
    resolved -- yes --> exact --> drain
    resolved -- no --> byname --> drain
    drain -- "worklist empty" --> virtual
    virtual -- "new methods kept" --> drain
    virtual -- "nothing new" --> drop --> explain --> files --> members --> imports --> write --> dirs
```

## Where Things Live

```mermaid
flowchart LR
    subgraph repo["ApprovalTests.Java repo"]
        module["approvaltests-partial/<br/>src, pom.xml,<br/>CreatePartailApprovals.sh, build_and_test.sh"]
        dotlocal["approvaltests-partial/.local/ (gitignored)<br/>approvaltests-31.0.0/<br/>approvaltests-master/"]
        spec["internal_documentation/micro_features/<br/>PartialEvalutaion.md"]
    end
    subgraph project["Your project"]
        selection["approvaltests-selection.json"]
        output["target/approvaltests-custom/<br/>jar + report"]
    end
    tmp[("java.io.tmpdir/approvaltests-partial-test/<br/>TreeShakerTest checkout")]

    module -- "runs from" --> selection
    module -- "checks out into" --> dotlocal
    module -- "writes" --> output
    module -. "tests use" .-> tmp
```

- **Checkouts:** `.local/` sits inside the repo so you can browse the shaken code. `CheckedExceptionsTest` skips `.local` folders, because it scans the repo for ApprovalTests sources.
- **Tests:** `TreeShakerTest` clones this repo's own `31.0.0` tag from the local path into the temp directory, so it runs offline.
