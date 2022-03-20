# treeCmp

TreeCmp: comparison of trees in polynomial time. See the [manual](TreeCmp_manual.pdf) for usage.

## Compilation from the command line

Normally, compilation is done via an IDE like NetBeans or Eclipse.  However, to compile via the command line, follow these steps:

Recursively clone the repository
```
git clone --recursive https://github.com/TreeCmp/TreeCmp.git
```
cd into the cloned repository and make output diriectories
```
cd TreeCmp && mkdir -p out/class
```
Export CLASSPATH to the jar's in the lib directory
```
export CLASSPATH=lib/commons-cli-1.4.jar:\
lib/commons-collections4-4.3.jar:\
lib/commons-compress-1.18.jar:\
lib/commons-io-2.6.jar:\
lib/poi-4.1.0.jar:\
lib/poi-ooxml-4.1.0.jar:\
lib/poi-ooxml-schemas-4.1.0.jar:\
lib/xmlbeans-3.1.0.jar:$CLASSPATH
```
Compile
```
javac -d out/class src/treecmp/*.java src/treecmp/*/*.java src/pal/*/*.java src/treecmp/metric/weighted/*.java src/distanceAlg1/*.java src/polyAlg/*.java
```

The resulting compiled files can be run directly, for example by issuing the command
`java -cp out/class:lib/commons-cli-1.4.jar treecmp.Main`
(you will need to replace the colon with a semicolon on windows systems). However, it is usually easier to create a stand-alone .jar executable as below.

## Creating a jar executable from the command line

The jar executable (e.g. `TreeCmp.jar`) should be created in the `bin` directory, as it expects to find the `config/config.xml` file one level above the directory in which the executable resides. Once the class files have been compiled in the `out/class` directory as above, this jar file can be created in the correct place using the MANIFEST.MF file in `src/META-INF`:

```
jar cvfm bin/TreeCmp.jar src/META-INF/MANIFEST.MF -C out/class/ .
```

As defined in the manifest, extra libraries are expected to be placed in `lib` directory in the same place as the jar file. An easy way to do this is to move or copy the `lib` folder (containing `commons-cli-1.4.jar`) into the `bin` directory. For example, on unix-like systems you could do `cp -a lib bin/`, on windows `xcopy lib bin\`.

Once the `commons-cli-1.4.jar` library has been copied to the correct place, the `TreeCmp.jar` file can be run as described in the [manual](TreeCmp_manual.pdf) (e.g. 
`java -jar bin/TreeCmp.jar -w 2 -d ms -i examples/beast/testBSP.newick -o testBSP.newick_w_2.out -I`
