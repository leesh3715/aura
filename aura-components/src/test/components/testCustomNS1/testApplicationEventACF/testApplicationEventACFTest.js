({
    labels : ["UnAdaptableTest"],

    componentCreated: {},

    setUp: function(cmp){
        this.componentCreated = cmp;
    },
    
    //Application event in same (custom), other custom, internal and privileged namespaces with Global access
    
    testFireAndAccessApplicationEventInSameNSWithGlobalAccess:{
        test: function(){
            var self = this;
            var appEventSameNSGlobalAccess = $A.get("e.testCustomNS1:applicationEventWithGlobalAccess");
            appEventSameNSGlobalAccess.setParams({"message": "Message attribute on event is set"});
            appEventSameNSGlobalAccess.fire();
            $A.test.addWaitFor(true, 
              function(){
                var actual = self.componentCreated.get("v.message1");
                var expected = "Message attribute on event is set";
                return (actual === expected);
            }); 
        }
    },
    
    testFireAndAccessApplicationEventInInternalNSWithGlobalAccess:{
        test: function(){
            var self = this;
            var appEventInternalNSGlobalAccess = $A.get("e.auratest:applicationEventWithGlobalAccess");
            appEventInternalNSGlobalAccess.setParams({"message": "Message attribute on event is set"});
            appEventInternalNSGlobalAccess.fire();
            $A.test.addWaitFor(true, 
                    function(){
                      var actual = self.componentCreated.get("v.message2");
                      var expected = "Message attribute on event is set";
                      return (actual === expected);
            }); 
        }
    },
    
    testFireAndAccessApplicationEventInOtherCustomNSWithGlobalAccess:{
        test: function(){
            var self = this;
            var appEventOtherCustomNSGlobalAccess = $A.get("e.testCustomNS2:applicationEventWithGlobalAccess");
            appEventOtherCustomNSGlobalAccess.setParams({"message": "Message attribute on event is set"});
            appEventOtherCustomNSGlobalAccess.fire();
            $A.test.addWaitFor(true, 
                    function(){
                      var actual = self.componentCreated.get("v.message3");
                      var expected = "Message attribute on event is set";
                      return (actual === expected);
            }); 
        }
    },
    testFireAndAccessApplicationEventInPrivilegedNSWithGlobalAccess:{
        test: function(){
            var self = this;
            var appEventPrivilegedNSGlobalAccess = $A.get("e.testPrivilegedNS1:applicationEventWithGlobalAccess");
            appEventPrivilegedNSGlobalAccess.setParams({"message": "Message attribute on event is set"});
            appEventPrivilegedNSGlobalAccess.fire();
            $A.test.addWaitFor(true, 
                    function(){
                      var actual = self.componentCreated.get("v.message4");
                      var expected = "Message attribute on event is set";
                      return (actual === expected);
            }); 
        }
    },
    
    //Access check failure: Application event in Internal namespace with Public access
    
    testFireAndAccessApplicationEventInInternalNSWithPublicAccess:{
        test: function(){
            $A.test.expectAuraError("Access Check Failed!");
            var completed = false;
            var appEventInternalNSPublicAccess = $A.get("e.auratest:applicationEventWithPublicAccess");
            
            this.waitForErrorModal(function() {
                $A.test.getPopOverErrorMessage($A.test.getAuraErrorMessage(),"\' is not visible to \'",
                        "Access Check Failed! EventService.getEventDef():'markup://auratest:applicationEventWithPublicAccess",
                            "markup://testCustomNS1:testApplicationEventACF");
            });
        }
    },
    
    //Access check failure: Application event in Internal namespace with Default access
    
    testFireAndAccessApplicationEventInInternalNSWithDefaultAccess:{
        test: function(){
            $A.test.expectAuraError("Access Check Failed!");
            var completed = false;
            var appEventInternalNSDefaultAccess = $A.get("e.auratest:applicationEventWithDefaultAccess");
            
            this.waitForErrorModal(function() {
                $A.test.getPopOverErrorMessage($A.test.getAuraErrorMessage(),"\' is not visible to \'",
                        "Access Check Failed! EventService.getEventDef():'markup://auratest:applicationEventWithDefaultAccess",
                            "markup://testCustomNS1:testApplicationEventACF");
            });
        }
    },
    
    //Access check failure: Application event in Internal namespace with Intrnal access
    
    testFireAndAccessApplicationEventInInternalNSWithInternalAccess:{
        test: function(){
            $A.test.expectAuraError("Access Check Failed!");
            var completed = false;
            var appEventInternalNSInternalAccess = $A.get("e.auratest:applicationEventWithInternalAccess");
            
            this.waitForErrorModal(function() {
                $A.test.getPopOverErrorMessage($A.test.getAuraErrorMessage(),"\' is not visible to \'",
                        "Access Check Failed! EventService.getEventDef():'markup://auratest:applicationEventWithInternalAccess",
                            "markup://testCustomNS1:testApplicationEventACF");
            });
        }
    },
    
    waitForErrorModal: function(callback) {
        $A.test.addWaitForWithFailureMessage(true,
            function(){
                var element = document.getElementById('auraErrorMask');
                var style = $A.test.getStyle(element, 'display');
                return style === 'block';
            },
            "Error Modal didn't show up.",
            callback);
    }
    
})