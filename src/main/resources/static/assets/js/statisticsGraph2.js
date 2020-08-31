$(function(){

	$('#print').on('click', function() {
		window.print();
	});

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

	callList(1);
	dataGraphType();

	$('.stat__map-tab').on('click', 'a.stat__tab-link', function(e) {
		var $parent = $(this).parent();
		var _index = $parent.index();
		var $content = $('.stat__tab-content li').eq(_index);

		$parent.add($content).addClass('active').siblings().removeClass('active');

		e.preventDefault();
	}).find('.stat__tab-link').eq(1).trigger('click');

});

function dataGraphType() {

	var num = $('#num').val();
	var params = {
		typeName:$('#typeName').val(),
		num:num
	};

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr) { xhr.setRequestHeader(header, token); });

	$.ajax({
		url:'/api/statistics/dataGraphType',
		type : 'post',
		data : params,
		cache:false,
		error:function(request){
			ajaxErrorMsg(request);
		},
		success: function(res){
			if (!Ajax.checkResult(res)) {
				return;
			}
			circle_graph_call(res.data.circle_data_columns);
			disaster_graph_call(res.data.disaster_data_columns);
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
			team_rank_graph_call(nameData,cntData);
			team_rank_graph_call2(nameData2,cntData2);

		}
	})
}

function callList(page) {
	page = page - 1;
	if (page < 0) page = 0;

	var perPage = 50;
	var perArea = 5;
	var totCnt = 0;

	var $schList = $('#statisList');
	var $totalCnt = $('#totalCnt');

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr) { xhr.setRequestHeader(header, token); });

	var num = $('#num').val();
	var params = {
		typeName:$('#typeName').val(),
		num:num
	};

	$.ajax({
		url:'/api/statistics/dataGraphTypeList?size='+ perPage + '&page=' + page,
		type : 'post',
		data : params,
		cache:false,
		error:function(request){
			ajaxErrorMsg(request);
		},
		success: function(res){
			if (!Ajax.checkResult(res)) {
				return;
			}

			//화면 출력
			totCnt = res.data.total_rows;
			$("#paging").jqueryPager({pageSize: perPage,
				pageBlock: perArea,
				currentPage: page + 1,
				pageTotal: totCnt,
				clickEvent: 'callList'});
			if (totCnt === 0) {
				$schList.empty().append('<tr class="t-c"><td colspan="9" align="center">조회된 데이터가 없습니다.</td></tr>');
				return;
			}
			$totalCnt.text(totCnt);

			var html = '';
			var i=0;
			html += '<thead class="c-retable--tablet-hidden">';
			html += '<tr>'
			if(num==="1") {
				html += '<th scope="col">번호</th>'
				html += '<th scope="col">재해·재난유형</th>'
				html += '<th scope="col">조사시설물</th>'
				html += '<th scope="col">출동요청기관</th>'
				html += '<th scope="col">출동지역</th>'
				html += '<th scope="col">작성자(부서)</th>'
				html += '<th scope="col">출동일(년.월.)</th>'
			}else{
				html += '<th scope="col">번호</th>'
				html += '<th scope="col">조사시설물</th>'
				html += '<th scope="col">재해·재난유형</th>'
				html += '<th scope="col">출동요청기관</th>'
				html += '<th scope="col">출동지역</th>'
				html += '<th scope="col">작성자(부서)</th>'
				html += '<th scope="col">출동일(년.월.)</th>'
			}
			html += '</tr>'
			html += '</thead>'
			html += '<tbody>'
			$.each(res.data.datalist, function (key, value) {
				if(num==="1") {
					html += '<tr >';
					html += '<td >'+ echoNull2Blank(value.id) +'</td>';
					html += '<td >'+ echoNull2Blank(value.arDisasterItem) +'</td>';
					html += '<td >'+ echoNull2Blank(value.arFacItem) +'</td>';
					html += '<td >'+ echoNull2Blank(value.arRelatedId) +'</td>';
					html += '<td >'+ echoNull2Blank(value.arLocationCityType) +'</td>';
					html += '<td >'+ echoNull2Blank(value.arWriter) + echoNull2Blank("("+res.data.writeTeam[i]+")") +'</td>';
					html += '<td >'+ echoNull2Blank(value.arIntoStart.substr(0,4)+"년 "+value.arIntoStart.substr(4,2)+"월") +'</td>';
					html += '</tr >';
					i++;
				}else{
					html += '<tr >';
					html += '<td >'+ echoNull2Blank(value.id) +'</td>';
					html += '<td >'+ echoNull2Blank(value.arFacItem) +'</td>';
					html += '<td >'+ echoNull2Blank(value.arDisasterItem) +'</td>';
					html += '<td >'+ echoNull2Blank(value.arRelatedId) +'</td>';
					html += '<td >'+ echoNull2Blank(value.arLocationCityType) +'</td>';
					html += '<td >'+ echoNull2Blank(value.arWriter) + echoNull2Blank("("+res.data.writeTeam[i]+")") +'</td>';
					html += '<td >'+ echoNull2Blank(value.arIntoStart.substr(0,4)+"년 "+value.arIntoStart.substr(4,2)+"월")+'</td>';
					html += '</tr >';
					i++;
				}
			});
			html += '</tbody>'
			$schList.html(html);

		}
	});
}

// 원형 그래프데이터
function circle_graph_call(circle_data_columns) {
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
function team_rank_graph_call(nameList,rankNamesCountNow) {
	c3.generate({
		bindto: "#rank_chart",
		data: {
			columns: [
				rankNamesCountNow
			],
			type: 'bar'
		},
		axis: {
			rotated: true,
			x: {
				type: 'category',
				categories: nameList
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

function team_rank_graph_call2(nameList,rankNamesCountPro){
	c3.generate({
		bindto: "#rank_chart2",
		data: {
			columns: [
				rankNamesCountPro
			],
			type: 'bar'
		},
		axis: {
			rotated: true,
			x: {
				type: 'category',
				categories: nameList
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
function disaster_graph_call(disaster_data_columns) {
	c3.generate({
		bindto: "#bar_chart1",
		data: {
			columns: disaster_data_columns,
			type: 'bar'
		},
		axis: {
			x: {
				type: 'category',
				categories: ['붕괴', '화재/폭발', '지진', '싱크홀', '교통사고', '홍수/가뭄', '환경오염']
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
	c3.generate({
		bindto: "#bar_chart2",
		data: {
			columns: fac_data_columns,
			type: 'bar'
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
			type: 'bar'
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
	c3.generate({
		bindto: "#bar_chart3",
		data: {
			columns: month_data_columns,
			type: 'bar'
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