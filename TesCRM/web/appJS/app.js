var app = angular.module('serviceCRM', []);

app.controller('myCRM', function($scope, $http) {
    $http({
        method: 'GET',
        url: 'http://localhost:8084/ActivitiBPMDatabaseAPI/process_List/repository/process-definition'
    }).then(function successCallback(response) {
        
        $scope.myData = response.data;
        
    }, function errorCallback(response) {
        
    });
    
    $scope.getActivitiList = function(obj){
        var processDeploymenyId = obj.target.parentNode.id;
        var processXmlId = obj.target.id;
  
        $http({
           method: 'GET',
           headers: {
            Authorization:  'Basic a2VybWl0Omtlcm1pdA=='
        },
           url: 'http://localhost:8084/activiti-rest/service/repository/deployments/'+processDeploymenyId+'/resourcedata/'+processXmlId+'',
           transformResponse : function(data) {
                return $.parseXML(data);
            }
        }).then(function successCallback(response) {
            
            var actvList = xmlToJson(response.data);
            console.log(actvList.definitions.process.userTask);
            $scope.activitiData = actvList.definitions.process.userTask;
        
        }, function errorCallback(response) {
            if(response.status = 401){ // If you have set 401
                console.log("Bad Response..");
            }
        });
        
    }
});

function xmlToJson(xml) {

            // Create the return object
            var obj = {};

            if (xml.nodeType == 1) { // element
                // do attributes
                if (xml.attributes.length > 0) {
                    obj["@attributes"] = {};
                    for (var j = 0; j < xml.attributes.length; j++) {
                        var attribute = xml.attributes.item(j);
                        obj["@attributes"][attribute.nodeName] = attribute.nodeValue;
                    }
                }
            } else if (xml.nodeType == 3) { // text
                obj = xml.nodeValue;
            }

            // do children
            // If just one text node inside
            if (xml.hasChildNodes() && xml.childNodes.length === 1 && xml.childNodes[0].nodeType === 3) {
                obj = xml.childNodes[0].nodeValue;
            }
            else if (xml.hasChildNodes()) {
                for (var i = 0; i < xml.childNodes.length; i++) {
                    var item = xml.childNodes.item(i);
                    var nodeName = item.nodeName;
                    if (typeof (obj[nodeName]) == "undefined") {
                        obj[nodeName] = xmlToJson(item);
                    } else {
                        if (typeof (obj[nodeName].push) == "undefined") {
                            var old = obj[nodeName];
                            obj[nodeName] = [];
                            obj[nodeName].push(old);
                        }
                        obj[nodeName].push(xmlToJson(item));
                    }
                }
            }
            return obj;
        }