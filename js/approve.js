/**
 * Respond to server JSON object.
 *
 */
function processApproveResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("result:" + result);
  var js = JSON.parse(result);

  var choiceJSON = js["choice"]; //need the alternative right?
  var choiceId = choiceJSON["choiceId"];
  var description = choiceJSON["description"];
  var alternatives = choiceJSON["alternatives"];
  var alts = new Array();
  var altsDesc = new Array();
  	for(var i=0;i<alternatives.length;i++){
  		alts[i] = alternatives[i];
  		altsDesc[i] = alternatives[i].description;
		altsApprove[i]= alternatives[i].approvers;
  	}
  
  var maxNumMembers = choiceJSON["numMembers"];
  var isCompleted = choiceJSON["isCompleted"];
  
  var status = js["httpCode"];
  
  if (status == 200) {
    // Update computation result
    //document.addForm.result.value = computation
    
    //this is where you output all of the values from the variables above
	//NEED TO FIX THIS!!!
	var approveList = document.getElementById("approve1").value;
	var newApprove = document.createElement('li');
	newApprove.appendChild(document.createTextNode(approveList));
	list.appendChild(newApprove);
    
    document.getElementById("alt1Desc").innerText = altsDesc[0];
	document.getElementById("alt2Desc").innerText = altsDesc[1];
	document.getElementById("alt3Desc").innerText = altsDesc[2];
	document.getElementById("alt4Desc").innerText = altsDesc[3];
	document.getElementById("alt5Desc").innerText = altsDesc[4];
    
    
    
  } else {
    var msg = js["error"];
    //document.addForm.result.value = "error:" + msg;
  }

  //refreshChoice();
}

function handleApproveClick(e, int) {
//need to grab their name and then display the name under approve and add one to the count
// need to pass in Choice ID, Member name, and Alternative Description
//  var button = e;
  var altNum = int;
  var name = document.getElementById("memberName").innerText;
  var choiceId = document.getElementById("choiceId").innerText;
  var description = document.getElementById("alt" + altNum + "Desc").innerText;




  var data = {};          //NEED TO CHANGE: Make this for a choice
  data["choiceId"] = choiceId;
  data["memberName"] = name;
  data["altDesc"] = description;

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", add_url, true);

  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    console.log(xhr);
    console.log(xhr.request);
    
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processApproveResponse(xhr.responseText);
    } else {
      processApproveResponse("N/A");
    }
  };
}
