var TestCRM3 = angular.module('serviceCRM', []);
TestCRM3.controller('TestCRM3', function($scope, $http) {
    
    //Get process List
    $http({
        method: 'GET',
        headers: {
            Authorization:  'Basic a2VybWl0Omtlcm1pdA=='
        },
        url: 'http://localhost:8084/activiti-rest/service/repository/process-definitions'
    }).then(function successCallback(response) { 
        $scope.myData = response.data.data;
        console.log(response.data.data);
    }, function errorCallback(response) {
    });
    //End of process List
    
    
     $scope.getActivitiList = function(obj){
        var processDeploymenyId = obj.target.parentNode.id;
        var process_name = obj.target.id;
        console.log(process_name);
        //var processXmlId = process_name.replace(/\s/g, '');
        
        if(process_name === "Pizza")
        {
           var process_def_key = "process";
           localStorage.setItem('pro_def_key', process_def_key);
        }
        else
        {
            var process_def_key = "";
            localStorage.clear('pro_def_key');
        }
        
        //hide process list on process select
         $("#processListActive").attr("class", "active");
         $('#processListActive a').attr('class', "collapsible-header waves-effect arrow-r");
         $('#processListActive div').attr('style', "display: none");
         
         //show activiti List
         $("#ActivitiListActive").attr("class", "");
         $('#ActivitiListActive a').attr('class', "collapsible-header waves-effect arrow-r active");
         $('#ActivitiListActive div').attr('style', "display: block"); 

        //Get Activiti List    
        //console.log(processDeploymenyId+" $ "+processXmlId);
        $http({
           method: 'GET',
           headers: {
            Authorization:  'Basic a2VybWl0Omtlcm1pdA=='
        },
           url: 'http://localhost:8084/activiti-rest/service/repository/deployments/'+processDeploymenyId+'/resourcedata/'+process_name+'.bpmn20.xml',
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
        var activityId = obj.target.parentNode.id;
        var form_path = obj.target.id;
        //console.log(form_path);
        
        var id_list = [];
        var activity_list = [];
        var chk_id = 1;
        var execution_id = new Array();
        if(form_path.length!=0)
        {
            window.open("http://localhost:8084/TestCRM_3/newWindow.html#form_id", "_blank", "toolbar=yes,scrollbars=yes, width=1400,height=700"); 
        }
        else
        {
            $('#table_data').attr('style', 'display:');
            $http({
                method: 'GET',
                headers: {
                    Authorization:  'Basic a2VybWl0Omtlcm1pdA=='
                },
                url: 'http://localhost:8084/activiti-rest/service/runtime/executions/'
            }).then(function successCallback(response) {
                var activity_list = [];
                var chk_id = 1;
                for(var i=0; i<response.data.data.length; i++)
                {
                    if(activityId===response.data.data[i].activityId)
                    {
                        var id = response.data.data[i].id
                        $.ajax({
                            type: 'GET',
                            beforeSend: function (xhr) {
                                xhr.setRequestHeader ("Authorization", "Basic " + btoa('kermit' + ":" + 'kermit'));
                            },
                            url: 'http://localhost:8084/activiti-rest/service/runtime/executions/'+response.data.data[i].id+'/variables',
                            dataType: 'json',
                            async: false,
                            success: function (data) {
                                
                                var activity_details = {};
                            
                                activity_details['id'] = id;
                                //activity_details['activity_task_id'] = localStorage.getItem('activity_task_id');
                                activity_details['contactnum'] = data[0].value;
                                activity_details['address'] = data[1].value;
                                activity_details['pizza'] = data[2].value;
                                activity_details['name'] = data[3].value;
                                activity_details['mailid'] = data[4].value;
                                
                                activity_list.push(activity_details);
                            }
                        });
                    }   
                }
                
                console.log(activity_list);
                $scope.activity_instance_list = activity_list;
            }, function errorCallback(response) {
        
            });
        }
    };
    
    var edit_data;
    $scope.updateSelection = function(position, entities, row) {
    angular.forEach(entities, function(subscription, index) {
        if (position != index){
          subscription.checked = false;  
        }
        else{
            edit_data = row;
        }
        });
    };
    
    $scope.get_selected_item_data = function(){
        console.log(edit_data);
        $scope.process_instance_id = edit_data.id;
        $scope.contact_num = edit_data.contactnum;
        $scope.address = edit_data.address;
        $scope.pizza_type = edit_data.pizza;
        $scope.name = edit_data.name;
        $scope.email_id = edit_data.mailid;
   };
    
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