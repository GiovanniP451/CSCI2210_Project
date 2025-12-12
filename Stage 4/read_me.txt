To compile and execute these java files:
1. Download and place all the files in the same directory.

2.For a Windows computer open a command prompt and move to the file location using the cd command.

3.In the prompt enter the following command to compile all the files in that location:
	javac *.java
   
This will use the complier to create the byte code. If it compiles correctly a new file will be created for each with a different file type:
    Main.class
   These folder will contain the byte code that can be used to run the program.

4. To execute the program we use the java virtual machine. In the command prompt enter java and then just the name of the Main file. 

   For this file it will look like this:
    java Main
          
  This uses the virtual machine to run the byte code to execute the java files.