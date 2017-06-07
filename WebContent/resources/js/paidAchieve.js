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
				dest.add(newOption);
				src.remove(count);
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
				if (tagTemp != null) {
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
	$.ajax({
		type : "GET",
		dataType : "json",
		url : "/SIM/program/v1/getTagValueDropDown/" + listindex,
		success : function(response) {
/*			$.each(response, function(i, response) {
				document.getElementById("prdvalue").options[i] = new Option(
						response.key, response.value);
			});*/
			
     		$.each(response, function (i, item) {
			    $('#prdvalue').append($('<option>', { 
			        value: item.key,
			        text : item.value 
			    }));
			});
		},
		failure : function(response) {
			alert("Wrongly done");
		}
	});
	return true;
}

function achDynamicdropdown(listindex) {
	document.getElementById("achPrdvalue").length = 0;
	$.ajax({
		type : "GET",
		dataType : "json",
		url : "/SIM/program/v1/getTagValueDropDown/" + listindex,
		success : function(response) {
/*			$.each(response, function(i, response) {
				document.getElementById("achPrdvalue").options[i] = new Option(
						response.key, response.value);
				
			});*/
			
     		$.each(response, function (i, item) {
			    $('#achPrdvalue').append($('<option>', { 
			        value: item.key,
			        text : item.value 
			    }));
			});
		},
		failure : function(response) {
			alert("Wrongly done");
		}
	});
	return true;
}

function listboxCopy(sourceID, destID) {

	var src = document.getElementById(sourceID);
	var dest = document.getElementById(destID);

	/**If already if some data is present, remove it.*/
	if (dest != null && dest.options.length > 0) {
		for (var count = 0; count < dest.options.length; count++) {
			try {
				dest.remove(count, null);
			} catch (error) {
				dest.remove(count);
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
		includeAchieve = includePaid;
	} else if (destID == 'achExclude') {
		excludeAchieve = excludePaid;
	}

	return false;
}
