var includePaid = {};
var excludePaid = {};
var includeAchieve = {};
var excludeAchieve = {};

function listbox_moveacross(sourceID, destID) {
	var tagMap = {};
	var add = false;
	var remove = false;
	var reassignNeeded = false;
	var elementId;
	if (destID == 'include') {
		tagMap = includePaid;
		if (includePaid == null) {
			reassignNeeded = true;
		}

		add = true;
		elementId = 'paidBasedOn';
	} else if (destID == 'exclude') {
		tagMap = excludePaid;
		if (excludePaid == null) {
			reassignNeeded = true;
		}
		add = true;
		elementId = 'paidBasedOn';
	} else if (destID == 'achInclude') {
		tagMap = includeAchieve;
		if (includeAchieve == null) {
			reassignNeeded = true;
		}
		add = true;
		elementId = 'achievedBasedOn';
	} else if (destID == 'achExclude') {
		tagMap = excludeAchieve;
		if (excludeAchieve == null) {
			reassignNeeded = true;
		}
		add = true;
		elementId = 'achievedBasedOn';
	}

	if (sourceID == 'include') {
		tagMap = includePaid;
		remove = true;
		elementId = 'paidBasedOn';
	} else if (sourceID == 'exclude') {
		tagMap = excludePaid;
		remove = true;
		elementId = 'paidBasedOn';
	} else if (sourceID == 'achInclude') {
		tagMap = includeAchieve;
		remove = true;
		elementId = 'achievedBasedOn';
	} else if (sourceID == 'achExclude') {
		tagMap = excludeAchieve;
		remove = true;
		elementId = 'achievedBasedOn';
	}

	var src = document.getElementById(sourceID);
	var dest = document.getElementById(destID);
	var tagId = document.getElementById(elementId).value;

	var picked1 = false;
	for (var count = 0; count < src.options.length; count++) {
		if (src.options[count].selected == true) {
			picked1 = true;
		}
	}

	if (picked1 == false) {
		alert("Please select an option to move.");
		return;
	}

	for (var count = 0; count < src.options.length; count++) {
		if (src.options[count].selected == true) {
			var option = src.options[count];
			var newOption = document.createElement("option");
			newOption.value = option.value;
			newOption.text = option.text;
			try {
				dest.add(newOption.value, null);
				src.remove(count, null);
			} catch (error) {
				if(destID == 'prdvalue' || destID == 'achPrdvalue'){
					var tagTemp = getTagPropertyValue(tagId, tagMap);
					if(tagTemp != null && tagTemp.indexOf(newOption.value) !== -1){
						dest.add(newOption);	
					}
					src.remove(count);
				}
				else{
					dest.add(newOption);
					src.remove(count);	
				}
			}
			if (add) {
				var tagTemp = getTagPropertyValue(tagId, tagMap);
				if (tagTemp != null) {
					var arrLength = tagTemp.length;
					tagTemp[arrLength] = newOption.value;
					tagMap[tagId] = tagTemp;
				} else {
					var arr = [];
					arr[0] = newOption.value;
					if (tagMap == null) {
						tagMap = {};

					}
					tagMap[tagId] = arr;
					if (reassignNeeded) {
						if (destID == 'include') {
							includePaid = tagMap;
						} else if (destID == 'exclude') {
							excludePaid = tagMap;
						} else if (destID == 'achInclude') {
							includeAchieve = tagMap;
						} else if (destID == 'achExclude') {
							excludeAchieve = tagMap;
						}
						reassignNeeded = false;
					}
				}
			}
			if (remove) {
				var tagTemp = getTagPropertyValue(tagId, tagMap);
				if (tagTemp != null && tagTemp.indexOf(newOption.value) !== -1) {
					removeFromArray(tagTemp, newOption.value);
					tagMap[tagId] = tagTemp;
				} else {

					var tagMapTemp = tagMap;
					for ( var key in tagMapTemp) {
						// skip loop if the property is from prototype
						if (!tagMap.hasOwnProperty(key))
							continue;

						var obj = tagMapTemp[key];
						removeFromArray(obj, newOption.value);
						tagMapTemp[key] = obj;
					}

					tagMap = tagMapTemp;
				}
			}

			count--;
		}
	}
	return false;
}

function removeFromArray(arr) {
	var what, a = arguments, L = a.length, ax;
	while (L > 1 && arr.length) {
		what = a[--L];
		while ((ax = arr.indexOf(what)) !== -1) {
			arr.splice(ax, 1);
		}
	}
	return arr;
}

function getTagPropertyValue(propertyName, tagMap1) {
	if (tagMap1 != null) {
		return tagMap1[propertyName];
	}
};

function listbox_selectall(listID, isSelect) {
	var listbox = document.getElementById(listID);
	for (var count = 0; count < listbox.options.length; count++) {
		listbox.options[count].selected = isSelect;
	}
}

function dynamicdropdown(listindex) {
	document.getElementById("prdvalue").length = 0;
	
	if(listindex === 'COMMERCIAL' || listindex === 'CONSUMER' || listindex === 'OTR'){
		$.ajax({
			type : "GET",
			dataType : "json",
			url : "/SIM/program/v1/getTagValue/" + listindex,
			success : function(response) {
				populateTagDetails(response, 'prdvalue');
			},
			failure : function(response) {
				alert("Wrongly done");
			}
		});	
		
	}
	else{
		dynamicDropdownCommon(listindex);
	}
	return true;
}

function populateTagDetails(response, elementId){
	if(response != null){
		if(response.includedMap != null){
			if(elementId == 'prdvalue'){
				/*removeOptions('include');
				removeOptions('exclude');*/
				fnPopulateTag(response.includedMap, 'include');	
			}
			else if(elementId === 'achPrdvalue'){
				/*removeOptions('achInclude');
				removeOptions('achExclude');*/
				fnPopulateTag(response.includedMap, 'achInclude');
			}
			
		}
		if(response.excludedMap != null){
			if(elementId == 'prdvalue'){
				fnPopulateTag(response.excludedMap, 'exclude');	
			}
			else if(elementId === 'achPrdvalue'){
				fnPopulateTag(response.excludedMap, 'achExclude');
			}
		}
	}	
}

/*function removeOptions(elementId){
	var dest = document.getElementById(elementId);
	*//**If already if some data is present, remove it.*//*
	if (dest != null && dest.options.length > 0) {
		var totalLength = dest.options.length;
		while(totalLength > 0){
			for (var count = 0; count < totalLength; count++) {
				try {
					dest.remove(count, null);
				} catch (error) {
					dest.remove(count);
				}
			}	
			totalLength = dest.options.length;
		}
	}
	
	var tagMap = {};
	if (true) {
		if (elementId == 'include') {
			includePaid = tagMap;
		} else if (elementId == 'exclude') {
			excludePaid = tagMap;
		} else if (elementId == 'achInclude') {
			includeAchieve = tagMap;
		} else if (elementId == 'achExclude') {
			excludeAchieve = tagMap;
		}
	}
	
}*/

function fnPopulateTag(valueMap, elementId){
    var includedList = valueMap;
	var dest = document.getElementById(elementId);
	var tagMap;
	var reassignNeeded = true;
	if (elementId == 'include') {
		tagMap = includePaid;
	} else if (elementId == 'exclude') {
		tagMap = excludePaid;
	} else if (elementId == 'achInclude') {
		tagMap = includeAchieve;
	} else if (elementId == 'achExclude') {
		tagMap = excludeAchieve;
	}
	
	if(includedList != null){
	    $.each(includedList, function (i, mapValue) {
		    $.each(mapValue, function (j, value) {
				var newOption=document.createElement("option");
				newOption.value=value;
				newOption.text=value;
				try{
					dest.add(newOption.value,null);
				}
				catch(error){
					if(dest == null){
						console.log("dest value cannot be null here. Having this logger for testing purpose.. value of element id : "+elementId);
						dest = document.getElementById(elementId);
					}
					var tagTemp = getTagPropertyValue(i, tagMap);
					if(tagTemp != null){
						if(tagTemp.indexOf(newOption.value) === -1){
							dest.add(newOption);
							var arrLength = tagTemp.length;
							tagTemp[arrLength] = newOption.value;
							tagMap[i] = tagTemp;	
						}
					}
					else{
						dest.add(newOption);
						var arr = [];
						arr[0] = newOption.value;
						if (tagMap == null) {
							tagMap = {};
						}
						tagMap[i] = arr;
					}
				}
		    });

			if (reassignNeeded) {
				if (elementId == 'include') {
					includePaid = tagMap;
				} else if (elementId == 'exclude') {
					excludePaid = tagMap;
				} else if (elementId == 'achInclude') {
					includeAchieve = tagMap;
				} else if (elementId == 'achExclude') {
					excludeAchieve = tagMap;
				}
			}
			
			/*var tagTemp = getTagPropertyValue(i, tagMap);
			if (tagTemp != null ) {
				if(tagTemp.indexOf(newOption.value) === -1){
					var arrLength = tagTemp.length;
					tagTemp[arrLength] = newOption.value;
					tagMap[i] = tagTemp;	
				}
			} else {
				var arr = [];
				arr[0] = newOption.value;
				if (tagMap == null) {
					tagMap = {};

				}
				tagMap[i] = arr;
				if (reassignNeeded) {
					if (elementId == 'include') {
						includePaid = tagMap;
					} else if (elementId == 'exclude') {
						excludePaid = tagMap;
					} else if (elementId == 'achInclude') {
						includeAchieve = tagMap;
					} else if (elementId == 'achExclude') {
						excludeAchieve = tagMap;
					}
				}
			}*/
		    
		    
		    /**Update respective map object to be sent to service.*/
		    /*var tagTemp = getTagPropertyValue(i, tagMap);
			if (tagTemp != null) {
				tagMap[i] = mapValue;
			} else {
				if (tagMap == null) {
					tagMap = {};
				}
				tagMap[i] = mapValue;
				if (reassignNeeded) {
					if (elementId == 'include') {
						includePaid = tagMap;
					} else if (elementId == 'exclude') {
						excludePaid = tagMap;
					} else if (elementId == 'achInclude') {
						includeAchieve = tagMap;
					} else if (elementId == 'achExclude') {
						excludeAchieve = tagMap;
					}
				}
			}*/
		});
	}
}

function dynamicDropdownCommon(listindex){
	
	$.ajax({
		type : "GET",
		dataType : "json",
		url : "/SIM/program/v1/getTagValueDropDown/" + listindex,
		success : function(response) {
			var tagTemp = getTagPropertyValue(listindex, includePaid);
			if(tagTemp == null){
				tagTemp = [];
			}
			tagTemp = tagTemp.concat(getTagPropertyValue(listindex, excludePaid));
			
			if(tagTemp == null || tagTemp.length == 0){
				$.each(response, function (i, item) {
				    $('#prdvalue').append($('<option>', { 
				        value: item.key,
				        text : item.value 
				    }));
				});
			}
			else{
				$.each(response, function (i, item) {
					if(tagTemp.indexOf(item.key) == -1){
						$('#prdvalue').append($('<option>', { 
					        value: item.key,
					        text : item.value 
					    }));	
					}
				});
			}
		},
		failure : function(response) {
			alert("Wrongly done");
		}
	});	
}


function achDynamicDropdownCommon(listindex){
	$.ajax({
		type : "GET",
		dataType : "json",
		url : "/SIM/program/v1/getTagValueDropDown/" + listindex,
		success : function(response) {
     		var tagTemp = getTagPropertyValue(listindex, includeAchieve);
			if(tagTemp == null){
				tagTemp = [];
			}
			tagTemp = tagTemp.concat(getTagPropertyValue(listindex, excludeAchieve));
			
			if(tagTemp == null || tagTemp.length == 0){
				$.each(response, function (i, item) {
				    $('#achPrdvalue').append($('<option>', { 
				        value: item.key,
				        text : item.value 
				    }));
				});
			}
			else{
				$.each(response, function (i, item) {
					if(tagTemp.indexOf(item.key) == -1){
						$('#achPrdvalue').append($('<option>', { 
					        value: item.key,
					        text : item.value 
					    }));	
					}
				});
			}
		},
		failure : function(response) {
			alert("Wrongly done");
		}
	});
	
}

function achDynamicdropdown(listindex) {
	document.getElementById("achPrdvalue").length = 0;

	if(listindex === 'COMMERCIAL' || listindex === 'CONSUMER' || listindex === 'OTR'){
		$.ajax({
			type : "GET",
			dataType : "json",
			url : "/SIM/program/v1/getTagValue/" + listindex,
			success : function(response) {
				populateTagDetails(response, 'achPrdvalue');
			},
			failure : function(response) {
				alert("Wrongly done");
			}
		});	
		
	}
	else{
		achDynamicDropdownCommon(listindex);
	}
	
	return true;
}

function listboxCopy(sourceID, destID) {

	var src = document.getElementById(sourceID);
	var dest = document.getElementById(destID);

	/**If already if some data is present, remove it.*/
	var destLength=dest.options.length;
	if (dest != null && destLength > 0) {
		for (var count = 0; count < destLength; count++) {
			try {
				//dest.remove(count, null);
				dest.remove(dest.length-1, null)
			} catch (error) {
				//dest.remove(count);
				dest.remove(dest.length-1);
			}
		}
	}
	
	if (src == null || src.options.length == 0) {
		return;
	}

	for (var count = 0; count < src.options.length; count++) {
		var option = src.options[count];
		var newOption = document.createElement("option");
		newOption.value = option.value;
		newOption.text = option.text;
		try {
			dest.add(newOption.value, null);
		} catch (error) {
			dest.add(newOption);
		}
	}
	
	if (destID == 'achInclude') {
		/*includeAchieve = includePaid;*/
		includeAchieve = {};
		for(var k in includePaid) includeAchieve[k]=includePaid[k];
		
	} else if (destID == 'achExclude') {
		/*excludeAchieve = excludePaid;*/
		excludeAchieve = {};
		for(var k in excludePaid) excludeAchieve[k]=excludePaid[k];
	}

	return false;
}
