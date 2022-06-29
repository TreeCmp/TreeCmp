# treeCmp

TreeCmp: comparison of trees in polynomial time. See the [manual](TreeCmp_manual.pdf) for usage.

This is a Gradle project with IntelliJ IDEA and can be easily compiled with this IDE.

## Clone the repository with submodules

In the first step of any compilation type, clone the repository with all submodules:
```
git clone --recursive https://github.com/TreeCmp/TreeCmp.git
```
## Compilation from the command line with IntelliJ IDEA

In the IDE, open the project by selecting the TreeCmp folder, then open the Gradle window using the menu: View | Tool Windows | Gradle.

In the Gradle window expand tree to TreeCmp | Tasks | bulid | jar and run jar item by double clicking.

## Compilation from the command line Gradle

To compile via the command line with Gradle, follow these steps:

Check that Gradle is installed:

```
gradle -v
```

If not, install gradle on your operating system according to these instructions: https://gradle.org/install/

Go to the TreeCmp project root directory:

```
cd TreeCmp
```

Build the executable using the command:

```
gradle jar
```

## Finding and executing a build product

The jar executable (e.g. `TreeCmp.jar`) should be created in the `build/lib` directory.

The executable file can be run as described in the [manual](TreeCmp_manual.pdf) (e.g. 
`java -jar bin/TreeCmp.jar -w 2 -d ms -i examples/beast/testBSP.newick -o testBSP.newick_w_2.out -I`
