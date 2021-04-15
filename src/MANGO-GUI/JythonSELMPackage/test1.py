import java.lang.Double
import org.atzberger.application.selm_builder.Atz_Struct_DataContainer

val = java.lang.Double(0.123456)

SM  = org.atzberger.application.selm_builder.Atz_Struct_DataContainer()

SM.setData('test', val);

SM.setData('subData.subData2', val);

displayStr = SM.toStringFields()

print "Fields of SM:"
print displayStr


displayStr = SM.toStringValues()

print "Values of SM:"
print displayStr
