<a id="top"></a>
# Removing EnvironmentAwareReporter

toc 

Before October 2023 "ApprovalTests 19.0.0" and before, 

```mermaid
classDiagram
    interface EnvironmentAwareReporter{
        +isWorkingInThisEnvironment(String forFile): boolean
    }
    interface ApprovalFailureReporter{
        +report(String received, String approved): void
    }
    EnvironmentAwareReporter <|-- ApprovalFailureReporter
```


