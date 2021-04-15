from java.awt import Color
from javax.swing import JButton, JFrame, JColorChooser

frame = JFrame()
tcc   = JColorChooser()
tcc.showDialog(frame, "Test", Color.red)

