var EditableTable = function () {

    return {

        //main function to initiate the module
        init: function () {
            function restoreRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);

                for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                    oTable.fnUpdate(aData[i], nRow, i, false);
                }

                oTable.fnDraw();
            }

            function editRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                jqTds[0].innerHTML = '<input type="text" class="form-control small" value="' + aData[0] + '">';
                jqTds[1].innerHTML = '<input type="text" class="form-control small" value="' + aData[1] + '">';
                jqTds[2].innerHTML = '<input type="text" class="form-control small" value="' + aData[2] + '">';
                if($(aData[3]).attr('id') == null){
                	jqTds[3].innerHTML = '<a class="edit" href="">Save</a>';
                }
                else{
                	jqTds[3].innerHTML = '<a id='+ $(aData[3]).attr('id') +' class="edit" href="">Save</a>';	
                }
                if($(aData[4]).attr('id') == null){
                	jqTds[4].innerHTML = '<a class="cancel" href="">Cancel</a>';
                }
                else{
                	jqTds[4].innerHTML = '<a id='+ $(aData[4]).attr('id') +' class="cancel" href="">Cancel</a>';	
                }
            }

            function saveRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                var jqAnchor = $('a', nRow);
                if($(jqAnchor[0]).attr('id') == null){
                	oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 3, false);
                }
                else{
                	oTable.fnUpdate('<a id='+ $(jqAnchor[0]).attr('id') +' class="edit" href="">Edit</a>', nRow, 3, false);	
                }
                if($(jqAnchor[1]).attr('id') == null){
                	oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 4, false);
                }
                else{
                	oTable.fnUpdate('<a id='+ $(jqAnchor[1]).attr('id') +' class="delete" href="">Delete</a>', nRow, 4, false);	
                }
                
                oTable.fnDraw();
            }

            function cancelEditRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                //oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 4, false);
                oTable.fnDraw();
            }

            var oTable = $('#editable-sample,#tier-schedule').dataTable({
                "aLengthMenu": [
                    [5, 10, 20, -1],
                    [5, 10, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 50,
                "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ records per page",
                    "oPaginate": {
                        "sPrevious": "Prev",
                        "sNext": "Next"
                    }
                },
                "aoColumnDefs": [{
                        'bSortable': true,
                        'aTargets': [0]
                    }
                ]
            });

            jQuery('#tier-schedule_wrapper .dataTables_filter input').addClass("form-control medium"); // modify table search input
            jQuery('#tier-schedule_wrapper .dataTables_length select').addClass("form-control xsmall"); // modify table per page dropdown

            var nEditing = null;

            $('#tier-schedule_new').click(function (e) {
                e.preventDefault();
               
               var aiNew = oTable.fnAddData(['', '', '',
                        '<a class="edit" href="">Edit</a>', '<a class="cancel" data-mode="new" href="">Cancel</a>'
                ]);
                var nRow = oTable.fnGetNodes(aiNew[0]);
                editRow(oTable, nRow);
                
                nEditing = nRow; 
               
            });

            $('#editable-sample a.delete,#tier-schedule a.delete').live('click', function (e) {
                e.preventDefault();
                var id = $(this).attr('id');
                if (confirm("Are you sure you want to delete this row ?") == false) {
                    return;
                }

                var nRow = $(this).parents('tr')[0];
                oTable.fnDeleteRow(nRow);
                
                $.ajax({
                url: '/SIM/program/v1/removeTier/'+id,
                type: "GET",
                dataType: 'json',
                success: function(data) {
                }
             	 });
                //alert("Deleted! Do not forget to do some ajax to sync with backend :)");
            });

            $('#editable-sample a.cancel,#tier-schedule a.cancel').live('click', function (e) {
                e.preventDefault();
                if ($(this).attr("data-mode") == "new") {
                    var nRow = $(this).parents('tr')[0];
                    oTable.fnDeleteRow(nRow);
                } else {
                    restoreRow(oTable, nEditing);
                    nEditing = null;
                }
            });

            $('#editable-sample a.edit,#tier-schedule a.edit').live('click', function (e) {
                e.preventDefault();
                var tierAmountType =  $('input:radio[name=optionsRadiosDenominationTier]:checked').val();

                /* Get the row as a parent of the link that was clicked on */
                var nRow = $(this).parents('tr')[0];
                if (nEditing !== null && nEditing != nRow) {
                    /* Currently editing - but not this row - restore the old before continuing to edit mode */
                    restoreRow(oTable, nEditing);
                    editRow(oTable, nRow);
                    nEditing = nRow;
                } else if (nEditing == nRow && this.innerHTML == "Save") {
                    /* Editing this row and want to save it */
                    saveRow(oTable, nEditing);
                    nEditing = null;
                    alert(" Please click on 'Save Changes' button to permanently  update these changes in database !!!");
                } else {
                    /* No edit in progress - let's start one */
                    editRow(oTable, nRow);
                    nEditing = nRow;
                }
                
            	if(tierAmountType == '%'){
            		$("#optionsRadiosPercentageTier1").prop('checked', true);
            	}
            	else{
            		$("#optionsRadiosDollarSignTier1").prop('checked', true);
            	}
            });
        }

    };

}();