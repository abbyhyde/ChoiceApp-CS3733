/**
 * Refresh constant list from server
 *
 *    GET list_url
 *    RESPONSE  list of [name, value, system] constants 
 */
function refreshChoiceList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", list_url, true);
   xhr.send();
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processListResponse(xhr.responseText);
    } else {
      processListResponse("N/A");
    }
  };
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processListResponse(result) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var choiceList = document.getElementById("choices");
  var list = js["choices"];

  var output = "";
  for (var i = 0; i < list.length; i++) {
    var choiceJson = list[i];
    console.log(choiceJson);
    
    var choiceId = choiceJson["choiceId"];   		  //choice ID
    var dateCreated = choiceJson["dateCreated"];	  // creation date
	if (choiceJson["isCompleted"]) {
		var isCompleted = "completed";
	} else {
		var isCompleted = "not completed";
	}
	
    output = output + "<div id=\"choice" + choiceId + "\"><b>" + choiceId + "</b>   " + dateCreated + "   "  + isCompleted + "<br></div>";
    
  }

  // Update computation result
  choiceList.innerHTML = output;
}

