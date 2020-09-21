$(function(){

	$('#print').on('click', function() {
		window.print();
	});

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr) { xhr.setRequestHeader(header, token); });

	$.ajax({
		url:'/api/statistics/dataGraph',
		type : 'post',
		cache:false,
		error:function(request){
			ajaxErrorMsg(request);
		},
		success: function(res){
			if (!Ajax.checkResult(res)) {
				return;
			}
			circle_graph_call(res.data.circle_data_columns);
			disaster_graph_call(res.data.nowYear,res.data.production,res.data.disaster_data_columns);
			fac_graph_call(res.data.fac_data_columns);
			team_graph_call(res.data.team_data_columns,res.data.teamsData);
			month_graph_call(res.data.month_data_columns);
			var nameData = [];
			var cntData = [];
			var nameData2 = [];
			var cntData2 = [];
			if(res.data.nameList.length>11){
				for(i=0; i<10; i++){
					nameData.push(res.data.nameList[i]);
				}
			}else{
				for(i=0; i<res.data.nameList.length; i++){
					nameData.push(res.data.nameList[i]);
				}
			}
			if(res.data.rankNamesCountNow.length>12){
				for(i=0; i<11; i++){
					cntData.push(res.data.rankNamesCountNow[i]);
				}
			}else{
				for(i=0; i<res.data.rankNamesCountNow.length; i++){
					cntData.push(res.data.rankNamesCountNow[i]);
				}
			}

			if(res.data.nameList2.length>11){
				for(i=0; i<10; i++){
					nameData2.push(res.data.nameList2[i]);
				}
			}else{
				for(i=0; i<res.data.nameList2.length; i++){
					nameData2.push(res.data.nameList2[i]);
				}
			}
			if(res.data.rankNamesCountPro.length>12){
				for(i=0; i<11; i++){
					cntData2.push(res.data.rankNamesCountPro[i]);
				}
			}else{
				for(i=0; i<res.data.rankNamesCountPro.length; i++){
					cntData2.push(res.data.rankNamesCountPro[i]);
				}
			}
			team_rank_graph_call(nameData,cntData,res.data.max1);
			team_rank_graph_call2(nameData2,cntData2,res.data.max2);
		}
	})

	$('.stat__map-tab').on('click', 'a.stat__tab-link', function(e) {
		var $parent = $(this).parent();
		var _index = $parent.index();
		var $content = $('.stat__tab-content li').eq(_index);

		$parent.add($content).addClass('active').siblings().removeClass('active');

		e.preventDefault();
	}).find('.stat__tab-link').eq(1).trigger('click');

	$("#productionYear").on('click',function () {
		$("#productionYear").css('background-color','#ffa143').css('color','#fff');
		$("#nowYear").css('background-color','#fff').css('color','#ffa143');
		$("#rank_chart2").css('display','block');
		$("#rank_chart").css('display','none');
	});

	$("#nowYear").on('click',function () {
		$("#nowYear").css('background-color','#ffa143').css('color','#fff');
		$("#productionYear").css('background-color','#fff').css('color','#ffa143');
		$("#rank_chart").css('display','block');
		$("#rank_chart2").css('display','none');
	});

});

// 원형 그래프데이터
function circle_graph_call(circle_data_columns) {
	// console.log("circle_data_columns : "+circle_data_columns);

	c3.generate({
		bindto: "#pie_chart1",
		data: {
			columns: circle_data_columns,
			type: 'pie'
		},
		tooltip: {
			format: {
				value: function (value) {
					return value + '건';
				}
			}
		}
	})
}

// 조사담당자 랭킹그래프
function team_rank_graph_call(nameList,rankNamesCountNow,max1) {

	var maxPlus = 0;
	if(max1<7) {
		maxPlus = Number(7-max1);
	}

	c3.generate({
		bindto: "#rank_chart",
		data: {
			columns: [
				rankNamesCountNow
			],
			type: 'bar',
			color: function () {
				return "#000000"
			}
		},
		axis: {
			x: {
				type: 'category',
				categories: nameList
			},
			y: {
				max: Number(max1+maxPlus)
			}
		},
		tooltip: {
			format: {
				value: function (value) {
					return value + '건';
				}
			}
		},
		size: {
			height: 500
		}
	})
}

function team_rank_graph_call2(nameList,rankNamesCountPro,max2){

	var maxPlus = 0;
	if(max2<7) {
		maxPlus = Number(7-max2);
	}

	c3.generate({
		bindto: "#rank_chart2",
		data: {
			columns: [
				rankNamesCountPro
			],
			type: 'bar',
			color: function () {
				return "#000000"
			}
		},

		axis: {
			x: {
				type: 'category',
				categories: nameList
			},
			y: {
				max: Number(max2+maxPlus)
			}
		},
		tooltip: {
			format: {
				value: function (value) {
					return value + '건';
				}
			}
		},
		size: {
			height: 500
		}
	})
}

// 재해재난 그래프데이터
function disaster_graph_call(now,production,disaster_data_columns) {
	var categories = ['붕괴', '화재/폭발', '지진', '싱크홀', '교통사고', '홍수/가뭄', '환경오염'];
	var colors = {
		"2019":'#000000',
		"2020":'#ff9b00',
	};

	c3.generate({
		bindto: "#bar_chart1",
		data: {
			columns: disaster_data_columns,
			type: 'bar',
			color: function(color, d) {
				if(typeof d === 'object') {
					return colors[d.id];
				}else {
					return colors[d];
				}
			}
		},
		axis: {
			x: {
				type: 'category',
				categories: categories
			}
		},
		tooltip: {
			format: {
				value: function (value) {
					return value + '건';
				}
			}
		}
	})
}

// 조사시설물 그래프데이터
function fac_graph_call(fac_data_columns) {
	var colors = {
		"2019":'#000000',
		"2020":'#ff9b00',
	};

	c3.generate({
		bindto: "#bar_chart2",
		data: {
			columns: fac_data_columns,
			type: 'bar',
			color: function(color, d) {
				if(typeof d === 'object') {
					return colors[d.id];
				}else {
					return colors[d];
				}
			}
		},
		axis: {
			x: {
				type: 'category',
				categories: ['교량', '옹벽', '비탈면', '터널', '도로', '기타도로시설', '건축물', '지반', '지하시설물', '기타']
			}
		},
		tooltip: {
			format: {
				value: function (value) {
					return value + '건';
				}
			}
		}
	})
}

// 부서별 출동현황 그래프데이터
function team_graph_call(team_data_columns,teamsData) {
	c3.generate({
		bindto: "#bar_team",
		data: {
			columns: [team_data_columns],
			type: 'bar',
			color: function () {
				return "#000000"
			}
		},
		axis: {
			x: {
				type: 'category',
				categories: teamsData
			}
		},
		tooltip: {
			format: {
				value: function (value) {
					return value + '건';
				}
			}
		},
		legend: {
			hide: true
		}
	})
}

// 월별 출동현황 그래프데이터
function month_graph_call(month_data_columns) {
	var colors = {
		"2019":'#000000',
		"2020":'#ff9b00',
	};

	c3.generate({
		bindto: "#bar_chart3",
		data: {
			columns: month_data_columns,
			type: 'bar',
			color: function(color, d) {
				if(typeof d === 'object') {
					return colors[d.id];
				}else {
					return colors[d];
				}
			}
		},
		axis: {
			x: {
				type: 'category',
				categories: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
			}
		},
		tooltip: {
			format: {
				value: function (value) {
					return value + '건';
				}
			}
		}
	})
}