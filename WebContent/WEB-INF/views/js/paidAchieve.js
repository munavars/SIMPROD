        function listbox_moveacross(sourceID,destID){
         var src=document.getElementById(sourceID);
            	var dest=document.getElementById(destID);
            	var picked1 = false;
            	for(var count=0;count<src.options.length;count++){
            		if(src.options[count].selected==true){picked1=true;}
            	}
            
            	if(picked1==false){alert("Please select an option to move.");return;}
            
            	for(var count=0;count<src.options.length;count++){
            		if(src.options[count].selected==true){var option=src.options[count];
            			var newOption=document.createElement("option");
            			newOption.value=option.value;
            			newOption.text=option.text;
            			try{dest.add(newOption.value,null);
            			src.remove(count,null);
            		}
            			catch(error){dest.add(newOption);src.remove(count);
            		}
            		count--;
            		}
            	}}         
           
         function dynamicdropdown(listindex)
         {
         document.getElementById("prdvalue").length = 0;
         switch (listindex)
             {
             case "01" :
         document.getElementById("prdvalue").options[0]=new Option("T","t");
                document.getElementById("prdvalue").options[1]=new Option("P","p");          
                 break;
             case "02" :
                document.getElementById("prdvalue").options[0]=new Option("M","m");
                document.getElementById("prdvalue").options[1]=new Option("E","e");
                document.getElementById("prdvalue").options[2]=new Option("N","n");
         document.getElementById("prdvalue").options[3]=new Option("F","f");
         document.getElementById("prdvalue").options[4]=new Option("C","c");
                document.getElementById("prdvalue").options[5]=new Option("K","k");
                document.getElementById("prdvalue").options[6]=new Option("L","l");
         document.getElementById("prdvalue").options[7]=new Option("D","d");
         document.getElementById("prdvalue").options[8]=new Option("A","a");
         document.getElementById("prdvalue").options[9]=new Option("I","i");
                document.getElementById("prdvalue").options[10]=new Option("B","b");
                document.getElementById("prdvalue").options[11]=new Option("J","j");
         document.getElementById("prdvalue").options[12]=new Option("G","g");
         document.getElementById("prdvalue").options[13]=new Option("H","h");
                 break;
         case "03" :
                document.getElementById("prdvalue").options[0]=new Option("JAB","jba");
                document.getElementById("prdvalue").options[1]=new Option("DAC","dac");
                document.getElementById("prdvalue").options[2]=new Option("AAH","aah");
         document.getElementById("prdvalue").options[3]=new Option("IAE","iae");
                document.getElementById("prdvalue").options[4]=new Option("DAT","dat");
                document.getElementById("prdvalue").options[5]=new Option("AAP","aap");
         document.getElementById("prdvalue").options[6]=new Option("EAP","eap");
                document.getElementById("prdvalue").options[7]=new Option("IAM","iam");
                document.getElementById("prdvalue").options[8]=new Option("LAA","laa");
         document.getElementById("prdvalue").options[9]=new Option("EAA","eaa");	
                 break;
         case "05" :
                document.getElementById("prdvalue").options[0]=new Option("00005","5");
                document.getElementById("prdvalue").options[1]=new Option("00007","7");
                document.getElementById("prdvalue").options[2]=new Option("00011","11");
         document.getElementById("prdvalue").options[3]=new Option("00013","13");
                document.getElementById("prdvalue").options[4]=new Option("00014","14");
                document.getElementById("prdvalue").options[5]=new Option("00015","15");
         document.getElementById("prdvalue").options[6]=new Option("00018","18");
                document.getElementById("prdvalue").options[7]=new Option("00019","19");
                document.getElementById("prdvalue").options[8]=new Option("00020","20");
         document.getElementById("prdvalue").options[9]=new Option("00021","21");			
                 break;
         case "06" :
                document.getElementById("prdvalue").options[0]=new Option("11785","1");
                document.getElementById("prdvalue").options[1]=new Option("11812","2");
                document.getElementById("prdvalue").options[2]=new Option("11829","3");
         document.getElementById("prdvalue").options[3]=new Option("11887","4");
                document.getElementById("prdvalue").options[4]=new Option("11930","5");
                document.getElementById("prdvalue").options[5]=new Option("11973","6");
         document.getElementById("prdvalue").options[6]=new Option("12010","7");
                document.getElementById("prdvalue").options[7]=new Option("12050","8");
                document.getElementById("prdvalue").options[8]=new Option("12135","9");
         document.getElementById("prdvalue").options[9]=new Option("12207","10");
                 break;
         case "07" :
                document.getElementById("prdvalue").options[0]=new Option("12710001","1");
                document.getElementById("prdvalue").options[1]=new Option("11793001","2");
                document.getElementById("prdvalue").options[2]=new Option("12691001","3");
         document.getElementById("prdvalue").options[3]=new Option("13465001","4");
                document.getElementById("prdvalue").options[4]=new Option("11989001","5");
                document.getElementById("prdvalue").options[5]=new Option("12155001","6");
         document.getElementById("prdvalue").options[6]=new Option("12331001","7");
                document.getElementById("prdvalue").options[7]=new Option("12332001","8");
                document.getElementById("prdvalue").options[8]=new Option("12367001","9");
         document.getElementById("prdvalue").options[9]=new Option("13272001","10");
                 break;
         case "08" :
                document.getElementById("prdvalue").options[0]=new Option("50102","1");
                document.getElementById("prdvalue").options[1]=new Option("90104","2");
                document.getElementById("prdvalue").options[2]=new Option("16302","3");
         document.getElementById("prdvalue").options[3]=new Option("90114","4");
                document.getElementById("prdvalue").options[4]=new Option("10110","5");
                document.getElementById("prdvalue").options[5]=new Option("32124","6");
         document.getElementById("prdvalue").options[6]=new Option("04848","7");
                document.getElementById("prdvalue").options[7]=new Option("31804","8");
                document.getElementById("prdvalue").options[8]=new Option("10551","9");
         document.getElementById("prdvalue").options[9]=new Option("05630","10");
                 break;
         case "09" :
                document.getElementById("prdvalue").options[0]=new Option("Y","b2");
                document.getElementById("prdvalue").options[1]=new Option("P","b3");
                 break;
         case "10" :
                document.getElementById("prdvalue").options[0]=new Option("CBL","cbl");
                document.getElementById("prdvalue").options[1]=new Option("FLP","flp");
                document.getElementById("prdvalue").options[2]=new Option("FOC","foc");
         document.getElementById("prdvalue").options[3]=new Option("HPT","hpt");
                document.getElementById("prdvalue").options[4]=new Option("LTB","ltb");
                document.getElementById("prdvalue").options[5]=new Option("LTC","ltc");
         document.getElementById("prdvalue").options[6]=new Option("LTR","ltr");
                document.getElementById("prdvalue").options[7]=new Option("MC","mc");
                document.getElementById("prdvalue").options[8]=new Option("OED","oed");
         document.getElementById("prdvalue").options[9]=new Option("ORL","orl");
                 break;
         case "11" :
                document.getElementById("prdvalue").options[0]=new Option("92996","1");
                document.getElementById("prdvalue").options[1]=new Option("67784","2");
                document.getElementById("prdvalue").options[2]=new Option("01595","3");
         document.getElementById("prdvalue").options[3]=new Option("11001","4");
                document.getElementById("prdvalue").options[4]=new Option("51003","5");
                document.getElementById("prdvalue").options[5]=new Option("08050","6");
         document.getElementById("prdvalue").options[6]=new Option("31010","7");
                document.getElementById("prdvalue").options[7]=new Option("08040","8");
                document.getElementById("prdvalue").options[8]=new Option("70103","9");
         document.getElementById("prdvalue").options[9]=new Option("30101","10");
                 break;
         case "12" :
                document.getElementById("prdvalue").options[0]=new Option("ENVigor","envi");
                document.getElementById("prdvalue").options[1]=new Option("ADRS","adr");
                document.getElementById("prdvalue").options[2]=new Option("S DRIVE","sdr");
         document.getElementById("prdvalue").options[3]=new Option("V262","v26");
                document.getElementById("prdvalue").options[4]=new Option("YK520","yk5");
                document.getElementById("prdvalue").options[5]=new Option("ADVANV105S","ad10");
         document.getElementById("prdvalue").options[6]=new Option("S208","5208");
                document.getElementById("prdvalue").options[7]=new Option("A008RS","a08");
         document.getElementById("prdvalue").options[8]=new Option("Y372","y32");
                 break;
         }
             return true;
         }
         
         function achDynamicdropdown(listindex)
         {
         document.getElementById("prdvalue").length = 0;
         switch (listindex)
             {
             case "01" :
         document.getElementById("achPrdvalue").options[0]=new Option("T","t");
                document.getElementById("achPrdvalue").options[1]=new Option("P","p");          
                 break;
             case "02" :
                document.getElementById("achPrdvalue").options[0]=new Option("M","m");
                document.getElementById("achPrdvalue").options[1]=new Option("E","e");
                document.getElementById("achPrdvalue").options[2]=new Option("N","n");
         document.getElementById("achPrdvalue").options[3]=new Option("F","f");
         document.getElementById("achPrdvalue").options[4]=new Option("C","c");
                document.getElementById("achPrdvalue").options[5]=new Option("K","k");
                document.getElementById("achPrdvalue").options[6]=new Option("L","l");
         document.getElementById("achPrdvalue").options[7]=new Option("D","d");
         document.getElementById("achPrdvalue").options[8]=new Option("A","a");
         document.getElementById("achPrdvalue").options[9]=new Option("I","i");
                document.getElementById("achPrdvalue").options[10]=new Option("B","b");
                document.getElementById("achPrdvalue").options[11]=new Option("J","j");
         document.getElementById("achPrdvalue").options[12]=new Option("G","g");
         document.getElementById("achPrdvalue").options[13]=new Option("H","h");
                 break;
         case "03" :
                document.getElementById("achPrdvalue").options[0]=new Option("JAB","jba");
                document.getElementById("achPrdvalue").options[1]=new Option("DAC","dac");
                document.getElementById("achPrdvalue").options[2]=new Option("AAH","aah");
         document.getElementById("achPrdvalue").options[3]=new Option("IAE","iae");
                document.getElementById("achPrdvalue").options[4]=new Option("DAT","dat");
                document.getElementById("achPrdvalue").options[5]=new Option("AAP","aap");
         document.getElementById("achPrdvalue").options[6]=new Option("EAP","eap");
                document.getElementById("achPrdvalue").options[7]=new Option("IAM","iam");
                document.getElementById("achPrdvalue").options[8]=new Option("LAA","laa");
         document.getElementById("achPrdvalue").options[9]=new Option("EAA","eaa");	
                 break;
         case "05" :
                document.getElementById("achPrdvalue").options[0]=new Option("00005","5");
                document.getElementById("achPrdvalue").options[1]=new Option("00007","7");
                document.getElementById("achPrdvalue").options[2]=new Option("00011","11");
         document.getElementById("achPrdvalue").options[3]=new Option("00013","13");
                document.getElementById("achPrdvalue").options[4]=new Option("00014","14");
                document.getElementById("achPrdvalue").options[5]=new Option("00015","15");
         document.getElementById("achPrdvalue").options[6]=new Option("00018","18");
                document.getElementById("achPrdvalue").options[7]=new Option("00019","19");
                document.getElementById("achPrdvalue").options[8]=new Option("00020","20");
         document.getElementById("achPrdvalue").options[9]=new Option("00021","21");			
                 break;
         case "06" :
                document.getElementById("achPrdvalue").options[0]=new Option("11785","1");
                document.getElementById("achPrdvalue").options[1]=new Option("11812","2");
                document.getElementById("achPrdvalue").options[2]=new Option("11829","3");
         document.getElementById("achPrdvalue").options[3]=new Option("11887","4");
                document.getElementById("achPrdvalue").options[4]=new Option("11930","5");
                document.getElementById("achPrdvalue").options[5]=new Option("11973","6");
         document.getElementById("achPrdvalue").options[6]=new Option("12010","7");
                document.getElementById("achPrdvalue").options[7]=new Option("12050","8");
                document.getElementById("achPrdvalue").options[8]=new Option("12135","9");
         document.getElementById("achPrdvalue").options[9]=new Option("12207","10");
                 break;
         case "07" :
                document.getElementById("achPrdvalue").options[0]=new Option("12710001","1");
                document.getElementById("achPrdvalue").options[1]=new Option("11793001","2");
                document.getElementById("achPrdvalue").options[2]=new Option("12691001","3");
         document.getElementById("achPrdvalue").options[3]=new Option("13465001","4");
                document.getElementById("achPrdvalue").options[4]=new Option("11989001","5");
                document.getElementById("achPrdvalue").options[5]=new Option("12155001","6");
         document.getElementById("achPrdvalue").options[6]=new Option("12331001","7");
                document.getElementById("achPrdvalue").options[7]=new Option("12332001","8");
                document.getElementById("achPrdvalue").options[8]=new Option("12367001","9");
         document.getElementById("achPrdvalue").options[9]=new Option("13272001","10");
                 break;
         case "08" :
                document.getElementById("achPrdvalue").options[0]=new Option("50102","1");
                document.getElementById("achPrdvalue").options[1]=new Option("90104","2");
                document.getElementById("achPrdvalue").options[2]=new Option("16302","3");
         document.getElementById("achPrdvalue").options[3]=new Option("90114","4");
                document.getElementById("achPrdvalue").options[4]=new Option("10110","5");
                document.getElementById("achPrdvalue").options[5]=new Option("32124","6");
         document.getElementById("achPrdvalue").options[6]=new Option("04848","7");
                document.getElementById("achPrdvalue").options[7]=new Option("31804","8");
                document.getElementById("achPrdvalue").options[8]=new Option("10551","9");
         document.getElementById("achPrdvalue").options[9]=new Option("05630","10");
                 break;
         case "09" :
                document.getElementById("achPrdvalue").options[0]=new Option("Y","b2");
                document.getElementById("achPrdvalue").options[1]=new Option("P","b3");
                 break;
         case "10" :
                document.getElementById("achPrdvalue").options[0]=new Option("CBL","cbl");
                document.getElementById("achPrdvalue").options[1]=new Option("FLP","flp");
                document.getElementById("achPrdvalue").options[2]=new Option("FOC","foc");
         document.getElementById("achPrdvalue").options[3]=new Option("HPT","hpt");
                document.getElementById("achPrdvalue").options[4]=new Option("LTB","ltb");
                document.getElementById("achPrdvalue").options[5]=new Option("LTC","ltc");
         document.getElementById("achPrdvalue").options[6]=new Option("LTR","ltr");
                document.getElementById("achPrdvalue").options[7]=new Option("MC","mc");
                document.getElementById("achPrdvalue").options[8]=new Option("OED","oed");
         document.getElementById("achPrdvalue").options[9]=new Option("ORL","orl");
                 break;
         case "11" :
                document.getElementById("achPrdvalue").options[0]=new Option("92996","1");
                document.getElementById("achPrdvalue").options[1]=new Option("67784","2");
                document.getElementById("achPrdvalue").options[2]=new Option("01595","3");
         document.getElementById("achPrdvalue").options[3]=new Option("11001","4");
                document.getElementById("achPrdvalue").options[4]=new Option("51003","5");
                document.getElementById("achPrdvalue").options[5]=new Option("08050","6");
         document.getElementById("achPrdvalue").options[6]=new Option("31010","7");
                document.getElementById("achPrdvalue").options[7]=new Option("08040","8");
                document.getElementById("achPrdvalue").options[8]=new Option("70103","9");
         document.getElementById("achPrdvalue").options[9]=new Option("30101","10");
                 break;
        case "12" :
                document.getElementById("achPrdvalue").options[0]=new Option("ENVigor","envi");
                document.getElementById("achPrdvalue").options[1]=new Option("ADRS","adr");
                document.getElementById("achPrdvalue").options[2]=new Option("S DRIVE","sdr");
         document.getElementById("achPrdvalue").options[3]=new Option("V262","v26");
                document.getElementById("achPrdvalue").options[4]=new Option("YK520","yk5");
                document.getElementById("achPrdvalue").options[5]=new Option("ADVANV105S","ad10");
         document.getElementById("achPrdvalue").options[6]=new Option("S208","5208");
                document.getElementById("achPrdvalue").options[7]=new Option("A008RS","a08");
         document.getElementById("achPrdvalue").options[8]=new Option("Y372","y32");
                 break;
         }
             return true;
         }
