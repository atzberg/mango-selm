import jpype

print("===============================================================================================");

# load the JAR file
print("Setting up the JVM and class path to JARs");
jvmPath   = "/usr/lib/jvm/java-6-openjdk/jre/lib/amd64/server/libjvm.so";
classPath = "-Djava.class.path=";
classPath = classPath + "/home/atzberg/research_linux/SELM_Builder/dist/SELM_Builder.jar";
classPath = classPath + ":/usr/lib/jvm/java-6-openjdk/jre/lib/rt.jar";
classPath = classPath + ":/usr/lib/jvm/java-6-openjdk/jre/lib/resources.jar";
classPath = classPath + ":/usr/share/netbeans/java4/modules/ext/appframework-1.0.3.jar";
classPath = classPath + ":/usr/share/netbeans/java4/modules/ext/swing-worker-1.1.jar";

jpype.startJVM(jvmPath, classPath)

# load main class package
print("Loading the application packages");
selm_builder = jpype.JPackage("org.atzberger.application.selm_builder");

application_Main = jpype.JClass("org.atzberger.application.selm_builder.application_Main");

# launches the application
print("Launching the application");
application_Main.launchNoArgs();

# gets instance of the application (not sure how this is determined exactly)
print("Getting application data : WARNING may need to call getApplSharedData() after application finishes launching. ");

Atz_Application_Data_Communication = jpype.JClass("org.atzberger.application.selm_builder.Atz_Application_Data_Communication");
applSharedData = Atz_Application_Data_Communication.getApplSharedData();  # allows for Python to control the application

String = jpype.JClass("java.lang.String");

sl = selm_builder.SELM_Lagrangian();

print("Done");
print("===============================================================================================");

def getApplSharedData():
  Atz_Application_Data_Communication = jpype.JClass("org.atzberger.application.selm_builder.Atz_Application_Data_Communication");
  applSharedData = Atz_Application_Data_Communication.getApplSharedData();  # allows for Python to control the application
  return applSharedData;
  
