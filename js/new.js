/**
 * Respond to server JSON object.
 *
 */
function processNewResponse(response) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("Choice ID:" + response);
  var js = JSON.parse(response);

  var choiceId = js["choiceId"];
  var status = js["httpCode"];
  
  if (status == 200) {
    // Update computation result
    document.newForm.choiceId.value = choiceId;
  } else {
    var msg = js["error"];
    document.newForm.choiceId.value = "error:" + msg;
  }
}

function handleNewClick(e) {
  var form = document.newForm;
  var desc = form.choiceDescription.value;
  var alt1 = form.alt1.value;
  var alt2 = form.alt2.value;
  var alt3 = form.alt3.value;
  var alt4 = form.alt4.value;
  var alt5 = form.alt5.value;
  var numTeam = form.numTeammates.value;


  var data = {};
  data["choiceId"] = "123";
  data["description"] = desc;
  data["alternatives"] = [
	{"description":alt1,
            "approvers":[],
		    "disapprovers":[],
		    "feedbacks":[]
	},
	{"description":alt2,
            "approvers":[],
		    "disapprovers":[],
		    "feedbacks":[]
	},
	{"description":alt3,
            "approvers":[],
		    "disapprovers":[],
		    "feedbacks":[]
	},
	{"description":alt4,
            "approvers":[],
		    "disapprovers":[],
		    "feedbacks":[]
	},
	{"description":alt5,
            "approvers":[],
		    "disapprovers":[],
		    "feedbacks":[]
	},
  ];
  data["maxNumMembers"] = numTeam;
  data["isCompleted"] = false;

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", new_url, true);

  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    console.log(xhr);
    console.log(xhr.request);
    
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processNewResponse(xhr.responseText);
    } else {
      processNewResponse("N/A");
    }
  };
}