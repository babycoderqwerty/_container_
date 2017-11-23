var app = angular.module('serviceCRM', []);

app.controller('myCRM', function($scope, $http) {
    
    //Get process List
    $http({
        method: 'GET',
        url: 'http://localhost:8084/ActivitiBPMDatabaseAPI/process_List/repository/process-definition'
    }).then(function successCallback(response) {
        
        $scope.myData = response.data;
        console.log(response.data);
        
    }, function errorCallback(response) {
        
    });
    
    //End of process List
    
    //Set Activiti List & peocess List Styling
    $scope.getActivitiList = function(obj){
        var processDeploymenyId = obj.target.parentNode.id;
        var processXmlId = obj.target.id;
        
        //hide process list on process select
         $("#processListActive").attr("class", "active");
         $('#processListActive a').attr('class', "collapsible-header waves-effect arrow-r");
         $('#processListActive div').attr('style', "display: none");
         
         //show activiti List
         $("#ActivitiListActive").attr("class", "");
         $('#ActivitiListActive a').attr('class', "collapsible-header waves-effect arrow-r active");
         $('#ActivitiListActive div').attr('style', "display: block"); 
 
        //Get Activiti List
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

            var count = actvList['definitions']['process']['userTask'].length;
            var user_task_id, task_name ,assignee, form_path;

            var activity_list = [];

            for (var i = 0; i < count; i++) {
                if (actvList['definitions']['process']['userTask'][i].hasOwnProperty('extensionElements')) {

                    if (actvList['definitions']['process']['userTask'][i]['extensionElements'].hasOwnProperty('activiti:formProperty')) {

                        if (actvList['definitions']['process']['userTask'][i]['@attributes'].hasOwnProperty('activiti:assignee')) {

                            user_task_id = actvList['definitions']['process']['userTask'][i]['@attributes']['id'];
                            task_name = actvList['definitions']['process']['userTask'][i]['@attributes']['name'];
                            assignee = actvList['definitions']['process']['userTask'][i]['@attributes']['activiti:assignee'];
                            form_path = "form_path";
                            //console.log(user_task_id);
                            
                        }
                        else {
                            user_task_id = actvList['definitions']['process']['userTask'][i]['@attributes']['id'];
                            task_name = actvList['definitions']['process']['userTask'][i]['@attributes']['name'];
                            assignee = actvList['definitions']['process']['userTask'][i]['@attributes']['activiti:candidateGroups'];
                            form_path = "";
                        }


                    }
                    else {

                        if (actvList['definitions']['process']['userTask'][i]['@attributes'].hasOwnProperty('activiti:assignee')) {

                            user_task_id = actvList['definitions']['process']['userTask'][i]['@attributes']['id'];
                            task_name = actvList['definitions']['process']['userTask'][i]['@attributes']['name'];
                            assignee = actvList['definitions']['process']['userTask'][i]['@attributes']['activiti:assignee'];
                            form_path = "";
                        }
                        else {
                            user_task_id = actvList['definitions']['process']['userTask'][i]['@attributes']['id'];
                            task_name = actvList['definitions']['process']['userTask'][i]['@attributes']['name'];
                            assignee = actvList['definitions']['process']['userTask'][i]['@attributes']['activiti:candidateGroups'];
                            form_path = "";
                        }
                    }

                }
                else {

                    if (actvList['definitions']['process']['userTask'][i]['@attributes'].hasOwnProperty('activiti:assignee')) {

                        user_task_id = actvList['definitions']['process']['userTask'][i]['@attributes']['id'];
                        task_name = actvList['definitions']['process']['userTask'][i]['@attributes']['name'];
                        assignee = actvList['definitions']['process']['userTask'][i]['@attributes']['activiti:assignee'];
                        form_path = "";
                    }
                    else {
                        user_task_id = actvList['definitions']['process']['userTask'][i]['@attributes']['id'];
                        task_name = actvList['definitions']['process']['userTask'][i]['@attributes']['name'];
                        assignee = actvList['definitions']['process']['userTask'][i]['@attributes']['activiti:candidateGroups'];
                        form_path = "";
                    }
                }
                
                var activity_details = {};
                
                activity_details['user_task_id'] = user_task_id;
                activity_details['task_name'] = task_name;
                activity_details['assignee'] = assignee;
                activity_details['form_path'] = form_path;
                
                activity_list.push(activity_details);
            }
            
            $scope.activitiData = activity_list;
            //console.log(activity_list);
        
        }, function errorCallback(response) {
            if(response.status = 401){ // If you have set 401
                console.log("Bad Response..");
            }
        });
        
        //End of Get Activiti List
    }
    
    $scope.loadActivityContents = function(obj){
        
        // check if activity is start activity and 
        // no instance can be lying at this activity
        // and any form path is associated
        
        var taskDefinitionKey = obj.target.parentNode.id;
        var form_path = obj.target.id;
        console.log(form_path);
        
        if(form_path.length!=0){
            //alert("form display");
            window.open("http://localhost:8084/TestCRM_1/newWindow.html#form_id", "_blank", "toolbar=yes,scrollbars=yes");
            
        }
        else{
            //alert("table display");
            $('#table_data').attr('style', 'display:');
            $http({
                method: 'GET',
                headers: {
                    Authorization:  'Basic a2VybWl0Omtlcm1pdA=='
                },
                url: 'http://localhost:8084/activiti-rest/service/runtime/tasks'
            }).then(function successCallback(response) {
                //console.log(response.data);
                activiti_response_count = response.data.data.length;
                //console.log(activiti_response_count);
            
                var activity_list = [];
                for(var i=0; i<activiti_response_count; i++){
                
                    if(taskDefinitionKey==response.data.data[i].taskDefinitionKey){
                    
                        var activiti_task_id = response.data.data[i].id;
                    
                        $http({
                            methos: 'GET',
                            headers: {
                                Authorization: 'Basic a2VybWl0Omtlcm1pdA=='
                            },
                            url: 'http://localhost:8084/activiti-rest/service/runtime/tasks/' + activiti_task_id + '/variables'
                        }).then(function successCallback(response){
                            //console.log(response.data.length);
                            //console.log(response.data);
                            var activity_details = {};
                
                            activity_details['contactnum'] = response.data[0].value;
                            activity_details['address'] = response.data[1].value;
                            activity_details['pizza'] = response.data[2].value;
                            activity_details['name'] = response.data[3].value;
                            activity_details['mailid'] = response.data[4].value;
                        
                            activity_list.push(activity_details);
                            //console.log(activity_list);
//                          for(var j=0; j<response.data.length; j++){
//                                
//                          }
                        
                        }, function errorCallback(response){
                            console.log(response);
                        });                    
                    }
               
                }
            
                console.log(activity_list);
                $scope.activity_instance_list = activity_list;
            }, function errorCallback(response) {
        
            });
        }
        
//        $http({
//            method: 'GET',
//            headers: {
//                Authorization:  'Basic a2VybWl0Omtlcm1pdA=='
//            },
//            url: 'http://localhost:8084/activiti-rest/service/runtime/tasks'
//        }).then(function successCallback(response) {
//            //console.log(response.data);
//            activiti_response_count = response.data.data.length;
//            //console.log(activiti_response_count);
//            
//            var activity_list = [];
//            for(var i=0; i<activiti_response_count; i++){
//                
//                if(taskDefinitionKey==response.data.data[i].taskDefinitionKey){
//                    
//                    var activiti_task_id = response.data.data[i].id;
//                    
//                    $http({
//                        methos: 'GET',
//                        headers: {
//                            Authorization: 'Basic a2VybWl0Omtlcm1pdA=='
//                        },
//                        url: 'http://localhost:8084/activiti-rest/service/runtime/tasks/' + activiti_task_id + '/variables'
//                    }).then(function successCallback(response){
//                        //console.log(response.data.length);
//                        //console.log(response.data);
//                        var activity_details = {};
//                
//                        activity_details['contactnum'] = response.data[0].value;
//                        activity_details['address'] = response.data[1].value;
//                        activity_details['pizza'] = response.data[2].value;
//                        activity_details['name'] = response.data[3].value;
//                        activity_details['mailid'] = response.data[4].value;
//                        
//                        activity_list.push(activity_details);
//                        //console.log(activity_list);
////                        for(var j=0; j<response.data.length; j++){
////                                
////                        }
//                        
//                    }, function errorCallback(response){
//                        console.log(response);
//                    });                    
//                }
//               
//            }
//            
//            console.log(activity_list);
//            $scope.activity_instance_list = activity_list;
//        }, function errorCallback(response) {
//        
//        });
         
    }
    
});


// convert XML to JSON

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