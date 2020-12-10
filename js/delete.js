/**
 * Refresh constant list from server
 *
 *    GET list_url
 *    RESPONSE  list of [name, value, system] constants 
 */
function handleDeleteClick() {
  var form = document.deleteForm;
  var days = form.dateId.value;

  var data = {};
  data["days"] = days;

  var js = JSON.stringify(data);
	
   var xhr = new XMLHttpRequest();
   xhr.open("POST", delete_url, true);
   xhr.send(js);
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processDeleteResponse(xhr.responseText);
    } else {
      processDeleteResponse("N/A");
    }
  };
}


/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processDeleteResponse(result) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var choiceList = document.getElementById("choices");
  var choiceID = document.getElementById("choiceID");
  var description = document.getElementById("description");
  var creation = document.getElementById("createdDate");
  var completed = document.getElementById("completed")
  var list = js["choices"];

  var output = "";
  var choicesID = "";
  var descripts = "";
  var created = "";
  var complete = "";

  for (var i = 0; i < list.length; i++) {
    var choiceJson = list[i];
    console.log(choiceJson);
    
    var choiceId = choiceJson["choiceId"];   		  //choice ID
    var desc = choiceJson["description"];
    var dateCreated = choiceJson["dateCreated"];	  // creation date
	if (choiceJson["isCompleted"]) {
		var isCompleted = "completed";
	} else {
		var isCompleted = "not completed";
	}
	
	choicesID += "<b>" + choiceId + "</b><br>"; 
	descripts += desc + "<br>";
	created += dateCreated + "<br>";
	complete += isCompleted + "<br>";
	
	
/*    //output = output + "<div id=\"choiceID" + choiceId + "\"><b>" + "<div id=\"description" + desc + "\"></b>" + "<div id=\"createdDate" + dateCreated + "\"></b>" + "<div id=\"completed" + isCompleted + "\"></b>" + "<br></div>";
    choicesID =  choicesID + "<div id=\"choiceID" + choiceId + "\">" + "<br></div>";
	descripts = descripts + "<div id=\"description" + desc + "\">" + "<br></div>";
	created = created + "<div id=\"createdDate" + dateCreated + "\">" + "<br></div>";
	complete = complete + "<div id=\"completed" + isCompleted + "\">" + "<br></div>";*/
	
  }

  // Update computation result
  //choiceList.innerHTML = output;
	choiceID.innerHTML = choicesID;
	description.innerHTML = descripts;
	creation.innerHTML = created;
	completed.innerHTML = complete;
}
