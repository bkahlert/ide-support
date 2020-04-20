//println(System.getProperty("JAVA_HOME"))
def javaHome = "/Library/Java/JavaVirtualMachines/jdk-13.0.2.jdk/Contents/Home/bin/java"
def command = javaHome + " -Dfile.encoding=UTF-8 -jar /Users/bkahlert/Development/com.bkahlert/ide-support/javadoc/build/libs/javadoc-1.0-SNAPSHOT.jar " + (binding.hasVariable("_1") ? this._1 : "MyFabulousTestClass")

def proc = command.execute()
def outputStream = new StringBuffer();
proc.waitForProcessOutput(outputStream, System.err)
println(outputStream) // for debugging if run directly
outputStream
