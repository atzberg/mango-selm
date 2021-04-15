class wrappedObject(object):
	
    def __init__(self, wrappedObj_in=None):
    	#print("executing wrappedObject init()");
        self.wrappedObj = wrappedObj_in;

    def setWrappedObj(self, wrappedObj_in):
    	self.wrappedObj = wrappedObj_in;
    	
    def getWrappedObj(self):
      return self.wrappedObj;
      

