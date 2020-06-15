<a id="top"></a>

# Configuration

toc

## What is configurable?
Configuration of ApprovalTests mainly occurs via @Annotations and PackageSettings. 
(the API is also extendible) 
Currently you can configure:

 * [Reporters](Reporters.md#class-and-method-level) (package/class/method)
 * FrontLoadedReporters ( package/class/method)
 * ApprovalSubdirectories (package)

## Using Subdirectories for Approval Output Files

Approved and received files can be stored in a preferred location. To do so, write a class as follows: 
snippet: package_settings_approval_subdirectory

The approved & received files will now be created in the subdirectory `/approvals/` for any test in the same package as this file, or any test in any subpackage under this.  

## Alternative Base Directory for Output Files  

Approved and received files can be stored in a different branch of the code base (for example, under the `/resources/` folder).

To do so, write a class as follows:    

snippet: package_settings_approval_base_directory

The approved and received files will now be created in the directory `/source/test/resources/` for any test in the same package as this file, or any test in any under this.  

## PackageLevelSettings  

Package Level Settings allows for programmatic setting of configuration at the package level. It follows the principle of least surprise.   

Your Package Leveling configuration must be in class called PackageSettings. The fields can be private, public and or static. They will be picked up regardless. All methods will be ignored.

For example if you had a class:

snippet: /approvaltests-tests/src/test/java/org/packagesettings/PackageSettings.java

If you where to call at the org.packagesettings level.

snippet: package_level_settings_get

Then you would get the following settings

snippet: /approvaltests-tests/src/test/java/org/packagesettings/PackageSettingsTest.testRetriveValue.approved.txt

However, if you also had

snippet: /approvaltests-tests/src/test/java/org/packagesettings/subpackage/PackageSettings.java

and you ran the same code but from the org.packagesettings.subpackage  
then you would get a blended view of the two classes where anything in the sub-package would override the parents.

snippet: /approvaltests-tests/src/test/java/org/packagesettings/subpackage/PackageSettingsTest.testRetriveValueWithOverRide.approved.txt

---

[Back to User Guide](README.md#top)
