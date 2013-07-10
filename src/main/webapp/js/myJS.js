$(function() {

		//callback function to bring a hidden box back
		function callback(){
			setTimeout(function(){
				$("#effect:visible").removeAttr('style').hide().fadeOut();
			}, 1000);
		};
		
		//set effect from select menu value
		$("#button").click(function() {
			var options = {};
			$("#effect").show("bounce",options,500,callback);
			return false;
		});
		
		$("#effect").hide();

        $("p").append("<strong>Hello</strong>");



        grid = new dhtmlXGridObject("box_grid");
        grid.setImagePath("js/dhtmlxSuite/dhtmlxGrid/codebase/imgs");
        grid.setHeader("Name,Surname");
        grid.setColTypes("ro,ro");
        grid.setSkin("light");
        grid.init();
        //grid.loadXML('DIController?op=show_services&softwa');

        grid.parse(document.getElementById('str_xml').value);


	});