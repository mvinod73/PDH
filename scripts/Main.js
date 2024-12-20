// require.config({
//     paths: {
//         vue: "./ETOWorkflow/Dependencies/vue/vue"
//     }
// });


define("PDH/scripts/Main", [
    "DS/PlatformAPI/PlatformAPI",
    "DS/WAFData/WAFData",
	"DS/DataDragAndDrop/DataDragAndDrop",	
    "css!PDH/Dependencies/bootstrap/css/bootstrap.css",
	"DS/WebappsUtils/WebappsUtils"],
    function (PlatformAPI, WAFData, DataDragAndDrop,dropIMG,util) {
		

        var myWidget = {
            
			
            onLoad: function () {
				
                myWidget.getData();   


                widget.setTitle("");

            },
			
			onRefresh: function() {
                        alert("Inside refresh");
                    },
            
            getData: function () {
				var appUrl = properties.applicationurl;
				alert("From prop file appUrl - "+appUrl);
				
				var iconUrl = widget.getUrl();
                iconUrl = iconUrl.substring(0, iconUrl.lastIndexOf("/"));
                var  dropIconUrl = iconUrl + "/assets/icons/I_DropZone.png";
                //let templateUrl = iconUrl+"/assets/BOM_Imports.xlsx";
				
				alert("heyyyyyyyyyyyyyyyyyy"+dropIconUrl);
				
                widget.body.innerHTML = "<div class='droppableFrame'><img id='dropImage' alt='Drop Here' src='"+dropIconUrl+"'></div><div class='droppedFrame'></div><iframe src='"+appUrl+"' title='description' style='width: 100vw; height: 100vh;'></iframe>";
				
				
				var theDropElt = widget.body.querySelector('.droppableFrame');		
				var theDroppedElt = widget.body.querySelector('.droppedFrame');
				
				
				DataDragAndDrop.droppable( theDropElt , {  
                drop : function(data) {
				    theDropElt.style.border = "none";
					var obj = JSON.parse(data);					
                    
                    var draggedObjType = obj["data"]["items"][0]["displayType"];
					var draggedObjName = obj["data"]["items"][0]["displayName"];
					var draggedObjId = obj["data"]["items"][0]["objectId"];
				   //alert("Inside Drop Type "+draggedObjType);
				   //alert("Inside Drop Name "+draggedObjName);
				   alert("Inside DropId "+draggedObjId);
				   console.log("----------------data---------",data);
				   
				   if(draggedObjType==="Change Action"){
					   alert("object type is Change Action............");
					   
					   //var iUrl = "https://emr-product-datahub-dev.azurewebsites.net/Dev/mcolist/";
					   var fUrl = properties.devcaurl+draggedObjId;
					   
					   theDroppedElt.innerHTML = "<iframe src='"+fUrl+"' title='description' style='width: 100vw; height: 100vh;'></iframe>";
					   
					   /*widget.body.innerHTML = "<div class='droppableFrame'>Drop Here </div><iframe src='"+fUrl+"' title='description' style='width: 100vw; height: 100vh;'></iframe>";*/
				   }
				   else if(draggedObjType==="MCO"){
					   alert("object type is MCO............");
					   
					   //var iMcoUrl = "https://emr-product-datahub-qa.azurewebsites.net/Dev/mcodetail/";
					   var fMcoUrl = properties.qamcodetailurl+draggedObjId;
					   
					   theDroppedElt.innerHTML = "<iframe src='"+fMcoUrl+"' title='description' style='width: 100vw; height: 100vh;'></iframe>";
					   
					   /*theDropElt.innerHTML = "<div class='droppableFrame'>Drop Here </div><iframe src='"+fMcoUrl+" title='description' style='width: 100vw; height: 100vh;'></iframe>";*/
					   
					   /*widget.body.innerHTML = `<div class="droppableFrame">Drop Here </div><iframe src="https://emr-product-datahub-qa.azurewebsites.net/Dev/mcodetail/1262836MMP03" title="description" style="width: 100vw; height: 100vh;"></iframe>`;*/
				   }
				   else {
					   alert("This functionality is not available for selected type");
				   }
				   
				   
				   
				   
                },
				enter: function() {	
                   theDropElt.style.border = "thick dotted #78befa";
				   
                },
                over: function() {	
                    theDropElt.style.border = "thick dotted #78befa";
					
                }, 
                leave: function() {
                   theDropElt.style.border = "none";
                }
            }) ;
            }
			

        }
		
        widget.myWidget = myWidget;
		widget.addEvent('onRefresh',  myWidget.onRefresh);
        return myWidget;
    });