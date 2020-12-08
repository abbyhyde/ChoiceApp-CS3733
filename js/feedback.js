/**
 * Respond to server JSON object.
 *
 */
function processFeedbackResponse(result) {
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
  var altsApprove = new Array();
  var altsDisapprove = new Array();
  var altsFeedback = new Array();
  var altsFeedbackDate = new Array();
  var altsFeedbackName = new Array();
  var altsFeedbackContents = new Array();
  	for(var i=0;i<alternatives.length;i++){
  		var currentApprove = new Array();
		var currentDisapprove = new Array();
		var currentFeedback = new Array();
		var currentFeedbackDate = new Array();
		var currentFeedbackName = new Array();
		var currentFeedbackContents = new Array();
  		alts[i] = alternatives[i];
  		altsDesc[i] = alternatives[i].description;
		for (var j=0; j<alternatives[i].approvers.length; j++) {
			currentApprove[j] = alternatives[i].approvers[j].name;
		}
		altsApprove[i] = currentApprove;
		for (var j=0; j<alternatives[i].disapprovers.length; j++) {
			currentDisapprove[j] = alternatives[i].disapprovers[j].name;
		}
		altsDisapprove[i] = currentDisapprove;
		for (var j=0; j<alternatives[i].feedbacks.length; j++) {
			currentFeedback[j] = alternatives[i].feedbacks[j];
			currentFeedbackDate[j] = alternatives[i].feedbacks[j].timeMade;
			currentFeedbackName[j] = alternatives[i].feedbacks[j].member.name;
			currentFeedbackContents[j] = alternatives[i].feedbacks[j].contents;
			//currentFeedback[j] = alternatives[i].feedbacks[j].timeMade.concat("  ", alternatives[i].feedback[j].name, "   ", alternatives[i].feedback[j].contents);
			//this doesn't wor try something else! lol
		}
		altsFeedback[i] = currentFeedback;
		altsFeedbackDate[i] = currentFeedbackDate;
		altsFeedbackName[i] = currentFeedbackName;
		altsFeedbackContents[i] = currentFeedbackContents;
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
	var name = document.getElementById("memberName").innerText; //need to check if this works!!!
	var approvers = new Array();   
	var disapprovers = new Array();
	var feedbacks = new Array(); 
	var feedbackDate = new Array();
	var feedbackName = new Array();
	var feedbackContent = new Array();

	if (altsDesc[0] == null) {
		document.getElementById("Alt1").style.visibility="hidden"; 
	} else {
		document.getElementById("Alt1").style.visibility="visible";
		document.getElementById("alt1Desc").innerText = altsDesc[0];
		if (altsApprove[0] != null) {
			approvers = altsApprove[0];
			var memNames = "";
			for (var i=0; i < approvers.length; i++) {
				memName = approvers[i];
				memNames += memName + "\n";
			}
			document.getElementById("approve1").innerHTML = memNames;
			document.getElementById("approveTot1").innerText = altsApprove[0].length;
		}
		if (altsDisapprove[0] != null) {
			disapprovers = altsDisapprove[0];
			var memNames = "";
			for (var i=0; i < disapprovers.length; i++) {
				memName = disapprovers[i];
				memNames += memName + "\n";
			}
			document.getElementById("disapprove1").innerHTML = memNames;
			document.getElementById("disapproveTot1").innerText = altsDisapprove[0].length;
		}
		if (altsFeedback[0] != null) {
			feedbacks = altsFeedback[0];
			/*feedbackDate = altsFeedbackDate[0];
			feedbackName = altsFeedbackName[0];
			feedbackContent = altsFeedbackContents[0];*/
			var feedback ="";
			for (var i=0; i < feedbacks.length; i++) {
				content = feedbacks[i].contents;
				name = feedbacks[i].member.name;
				date = feedbacks[i].timeMade;
				
				feedback += "<b>" + date + "&nbsp;&nbsp;&nbsp;&nbsp;" + name + "&nbsp;&nbsp;" + "</b>" + content + "<br>";
			}
			document.getElementById("Feedback1").innerHTML = feedback;
		}
	}

	if (altsDesc[1] == null) {
		document.getElementById("Alt2").style.visibility="hidden"; 
	} else {
		document.getElementById("Alt2").style.visibility="visible";
		document.getElementById("alt2Desc").innerText = altsDesc[1];
		if (altsApprove[1] != null) {
			approvers = altsApprove[1];
			var memNames = "";
			for (var i=0; i < approvers.length; i++) {
				memName = approvers[i];
				memNames += memName + "\n";
			}
			document.getElementById("approve2").innerHTML = memNames;
			document.getElementById("approveTot2").innerText = altsApprove[1].length;
		}
		if (altsDisapprove[1] != null) {
			disapprovers = altsDisapprove[1];
			var memNames = "";
			for (var i=0; i < disapprovers.length; i++) {
				memName = disapprovers[i];
				memNames += memName + "\n";
			}
			document.getElementById("disapprove2").innerHTML = memNames;
			document.getElementById("disapproveTot2").innerText = altsDisapprove[1].length;
		}
		if (altsFeedback[1] != null) {
			feedbacks = altsFeedback[1];
			/*feedbackDate = altsFeedbackDate[1];
			feedbackName = altsFeedbackName[1];
			feedbackContent = altsFeedbackContents[1];*/
			var feedback ="";
			for (var i=0; i < feedbacks.length; i++) {
				content = feedbacks[i].contents;
				name = feedbacks[i].member.name;
				date = feedbacks[i].timeMade;
				
				feedback += "<b>" + date + "&nbsp;&nbsp;&nbsp;&nbsp;" + name + "&nbsp;&nbsp;" + "</b>" + content + "<br>";
			}
			document.getElementById("Feedback2").innerHTML = feedback;
		}
	}
	
	
	if (altsDesc[2] == null) {
		document.getElementById("Alt3").style.visibility="hidden"; 
	} else {
		document.getElementById("Alt3").style.visibility="visible";
		document.getElementById("alt3Desc").innerText = altsDesc[2];
		if (altsApprove[2] != null) {
			approvers = altsApprove[2];
			var memNames = "";
			for (var i=0; i < approvers.length; i++) {
				memName = approvers[i];
				memNames += memName + "\n";
			}
			document.getElementById("approve3").innerHTML = memNames;
			document.getElementById("approveTot3").innerText = altsApprove[2].length;
		}
		if (altsDisapprove[2] != null) {
			disapprovers = altsDisapprove[2];
			var memNames = "";
			for (var i=0; i < disapprovers.length; i++) {
				memName = disapprovers[i];
				memNames += memName + "\n";
			}
			document.getElementById("disapprove3").innerHTML = memNames;
			document.getElementById("disapproveTot3").innerText = altsDisapprove[2].length;
		}
		if (altsFeedback[2] != null) {
			feedbacks = altsFeedback[2];
			/*feedbackDate = altsFeedbackDate[2];
			feedbackName = altsFeedbackName[2];
			feedbackContent = altsFeedbackContents[2];*/
			var feedback ="";
			for (var i=0; i < feedbacks.length; i++) {
				content = feedbacks[i].contents;
				name = feedbacks[i].member.name;
				date = feedbacks[i].timeMade;
				
				feedback += "<b>" + date + "&nbsp;&nbsp;&nbsp;&nbsp;" + name + "&nbsp;&nbsp;" + "</b>" + content + "<br>";
			}
			document.getElementById("Feedback3").innerHTML = feedback;
		}
	}
	
	
	
	if (altsDesc[3] == null) {
		document.getElementById("Alt4").style.visibility="hidden"; 
	} else {
		document.getElementById("Alt4").style.visibility="visible";
		document.getElementById("alt4Desc").innerText = altsDesc[3];
		if (altsApprove[3] != null) {
			approvers = altsApprove[3];
			var memNames = "";
			for (var i=0; i < approvers.length; i++) {
				memName = approvers[i];
				memNames += memName + "\n";
			}
			document.getElementById("approve4").innerHTML = memNames;
			document.getElementById("approveTot4").innerText = altsApprove[3].length;
		}
		if (altsDisapprove[3] != null) {
			disapprovers = altsDisapprove[3];
			var memNames = "";
			for (var i=0; i < disapprovers.length; i++) {
				memName = disapprovers[i];
				memNames += memName + "\n";
			}
			document.getElementById("disapprove4").innerHTML = memNames;
			document.getElementById("disapproveTot4").innerText = altsDisapprove[3].length;
		}
		if (altsFeedback[3] != null) {
			feedbacks = altsFeedback[3];
			/*feedbackDate = altsFeedbackDate[3];
			feedbackName = altsFeedbackName[3];
			feedbackContent = altsFeedbackContents[3];*/
			var feedback ="";
			for (var i=0; i < feedbacks.length; i++) {
				content = feedbacks[i].contents;
				name = feedbacks[i].member.name;
				date = feedbacks[i].timeMade;
				
				feedback += "<b>" + date + "&nbsp;&nbsp;&nbsp;&nbsp;" + name + "&nbsp;&nbsp;" + "</b>" + content + "<br>";
			}
			document.getElementById("Feedback4").innerHTML = feedback;
		}
	}	
	
	
	
	if (altsDesc[4] == null) {
		document.getElementById("Alt5").style.visibility="hidden"; 
	} else {
		document.getElementById("Alt5").style.visibility="visible";
		document.getElementById("alt5Desc").innerText = altsDesc[4];
	    if (altsApprove[4] != null) {
			approvers = altsApprove[4];
			var memNames = "";
			for (var i=0; i < approvers.length; i++) {
				memName = approvers[i];
				memNames += memName + "\n";
			}
			document.getElementById("approve5").innerHTML = memNames;
			document.getElementById("approveTot5").innerText = altsApprove[4].length;
		}
		if (altsDisapprove[4] != null) {
			disapprovers = altsDisapprove[4];
			var memNames = "";
			for (var i=0; i < disapprovers.length; i++) {
				memName = disapprovers[i];
				memNames += memName + "\n";
			}
			document.getElementById("disapprove5").innerHTML = memNames;
			document.getElementById("disapproveTot5").innerText = altsDisapprove[4].length;
		}
		if (altsFeedback[4] != null) {
			feedbacks = altsFeedback[4];
			/*feedbackDate = altsFeedbackDate[4];
			feedbackName = altsFeedbackName[4];
			feedbackContent = altsFeedbackContents[4];*/
			var feedback ="";
			for (var i=0; i < feedbacks.length; i++) {
				content = feedbacks[i].contents;
				name = feedbacks[i].member.name;
				date = feedbacks[i].timeMade;
				
				feedback += "<b>" + date + "&nbsp;&nbsp;&nbsp;&nbsp;" + name + "&nbsp;&nbsp;" + "</b>" + content + "<br>";
			}
			document.getElementById("Feedback5").innerHTML = feedback;
		}
	}	
	
       
  } else {
    var msg = js["error"];
    //document.addForm.result.value = "error:" + msg;
  }

  //refreshChoice();
}

function handleFeedbackClick(e, int) {
  var altNum = int;  
  var name = document.getElementById("memberName").innerText;
  var choiceId = document.getElementById("choiceId").innerText;
  var description = document.getElementById("alt" + altNum + "Desc").innerText;

	if (altNum == 1) {
		var form = document.addFeedback1;
	} else if (altNum == 2) {
		var form = document.addFeedback2;
	} else if (altNum == 3) {
		var form = document.addFeedback3;
	} else if (altNum == 4) {
		var form = document.addFeedback4;
	} else if (altNum == 5) {
		var form = document.addFeedback5;
	}
	
    var contents = form.feedback.value;

  var data = {};          //NEED TO CHANGE: Make this for a choice
  data["choiceId"] = choiceId;
  data["altDesc"] = description;
  data["memberName"] = name;
  data["feedbackDesc"] = contents;

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", feedback_url, true);

  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    console.log(xhr);
    console.log(xhr.request);
    
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processFeedbackResponse(xhr.responseText);
    } else {
      processFeedbackResponse("N/A");
    }
  };
}
