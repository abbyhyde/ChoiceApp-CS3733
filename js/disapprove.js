/**
 * Respond to server JSON object.
 *
 */
function processDisapproveResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("result:" + result);
  var js = JSON.parse(result);

  var choiceJSON = js["choice"];
  var choiceId = choiceJSON["choiceId"];
  var description = choiceJSON["description"];
  var alternatives = choiceJSON["alternatives"];
  var alts = new Array();
  var altsDesc = new Array();
  	for(var i=0;i<alternatives.length;i++){
  		alts[i] = alternatives[i];
  		altsDesc[i] = alternatives[i].description;
		altsApprove[i] = alternatives[i].approvers;
		altsDisapprove[i] = alternatives[i].disapprovers;
		altsFeedback[i] = alternatives[i].feedbacks;
  	}
  
  var maxNumMembers = choiceJSON["numMembers"];
  var isCompleted = choiceJSON["isCompleted"];
  
  var status = js["httpCode"];
  
  if (status == 200) {
    // Update computation result
    //document.addForm.result.value = computation
    
    //this is where you output all of the values from the variables above
    document.getElementById("choiceId").innerText = choiceId;
    document.getElementById("choiceDesc").innerText = description;
	document.getElementById("memberName").innerText = name;
    
    document.getElementById("alt1Desc").innerText = altsDesc[0];
	document.getElementById("approve1").innerText = document.getElementById("approve1").innerText; //probably don't need these lines
	document.getElementById("approveTot1").innerText = altsApprove[0].length;
	for (var i=0; i < altsDisapprove[0].length; i++) {
		memName = altsDisapprove[i].name;
		listMem = document.getElementById("disapprove1").innerText;
		document.getElementById("disapprove1").innerText = listMem + "\n" + memName;
	}
	document.getElementById("disapproveTot1").innerText = altsDisapprove[0].length;


	document.getElementById("alt2Desc").innerText = altsDesc[1];
	document.getElementById("approve2").innerText = document.getElementById("approve2").innerText; //probably don't need these lines
	document.getElementById("approveTot2").innerText = altsApprove[1].length;
	for (var i=0; i < altsDisapprove[1].length; i++) {
		memName = altsDisapprove[i].name;
		listMem = document.getElementById("disapprove2").innerText;
		document.getElementById("disapprove2").innerText = listMem + "\n" + memName;
	}
	document.getElementById("disapproveTot2").innerText = altsDisapprove[1].length;
	
	
	document.getElementById("alt3Desc").innerText = altsDesc[2];
	document.getElementById("approve3").innerText = document.getElementById("approve3").innerText; //probably don't need these lines
	document.getElementById("approveTot3").innerText = altsApprove[2].length;
	for (var i=0; i < altsDisapprove[2].length; i++) {
		memName = altsDisapprove[i].name;
		listMem = document.getElementById("disapprove3").innerText;
		document.getElementById("disapprove3").innerText = listMem + "\n" + memName;
	}
	document.getElementById("disapproveTot3").innerText = altsDisapprove[2].length;
	
	
	document.getElementById("alt4Desc").innerText = altsDesc[3];
	document.getElementById("approve4").innerText = document.getElementById("approve4").innerText; //probably don't need these lines
	document.getElementById("approveTot4").innerText = altsApprove[3].length;
	for (var i=0; i < altsDisapprove[3].length; i++) {
		memName = altsDisapprove[i].name;
		listMem = document.getElementById("disapprove4").innerText;
		document.getElementById("disapprove4").innerText = listMem + "\n" + memName;
	}
	document.getElementById("disapproveTot4").innerText = altsDisapprove[3].length;
	
	
	document.getElementById("alt5Desc").innerText = altsDesc[4];
    document.getElementById("approve5").innerText = document.getElementById("approve5").innerText; //probably don't need these lines
	document.getElementById("approveTot5").innerText = altsApprove[4].length;
	for (var i=0; i < altsDisapprove[4].length; i++) {
		memName = altsDisapprove[i].name;
		listMem = document.getElementById("disapprove5").innerText;
		document.getElementById("disapprove5").innerText = listMem + "\n" + memName;
	}
	document.getElementById("disapproveTot5").innerText = altsDisapprove[4].length;
    
    
    
  } else {
    var msg = js["error"];
  }
}

function handleDisapproveClick(e, int) {
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
  xhr.open("POST", disapprove_url, true);

  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    console.log(xhr);
    console.log(xhr.request);
    
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processDisapproveResponse(xhr.responseText);
    } else {
      processDisapproveResponse("N/A");
    }
 
  };
}
