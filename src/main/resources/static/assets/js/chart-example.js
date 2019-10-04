$(document).ready(function() {

	// pie chart
	var pie = c3.generate({
		bindto: "#pie_chart",
		data: {
			columns: [
				['정부·지자체', 5],
				['타 기관', 3],
				['KICT 자체', 13]
			],
			type: 'pie'
		},
		tooltip: {
			format: {
				value: function(value, ratio, id) {
					return value + '건';
				}
			}
		}
	});
	
	// bar chart type: 년도별 유형
	var chart = c3.generate({
		bindto: "#bar_chart",
		data: {
			columns: [
				['2018', 1, 1, 1, 1, 1, 1, 1],
				['2019', 5, 5, 3, 1, 1, 1, 1]
			],
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
				value: function(value, ratio, id) {
					return value + '건';
				}
			}
		}
	});
	
	// 부서별 출동 현황
	var chart2 = c3.generate({
		bindto: "#bar_team",
		data: {
			columns: [
				[' ', 5, 5, 3, 1, 1, 1, 1, 1, 1]
			],
			type: 'bar'
		},
		axis: {
			x: {
				type: 'category',
				categories: ['노후인프라센터', '인프라안전연구본부', '국토보전연구본부', '미래융합연구본부', '국민생활연구본부', '화재안전연구소', '연구전략기획본부', '스마트시티연구센터', '기타']
			}
		},
		tooltip: {
			format: {
				value: function(value, ratio, id) {
					return value + '건';
				}
			}
		},
		legend: {
			hide: true
		}
	});
	
	
	
	
	var chart2 = c3.generate({
		bindto: "#bar_chart2",
		data: {
			columns: [
				['2018', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
				['2019', 5, 5, 3, 1, 1, 1, 1, 1, 1, 1]
			],
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
				value: function(value, ratio, id) {
					return value + '건';
				}
			}
		}
	});
	
	var chart3 = c3.generate({
		bindto: "#bar_chart3",
		data: {
			columns: [
				['2018', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
				['2019', 1, 2, 3, 4, 5, 6, 6, 5, 4, 3, 2, 1]
			],
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
				value: function(value, ratio, id) {
					return value + '건';
				}
			}
		}
	});
	
	/*
	var ctx = document.getElementById('myChart');
	var myChart = new Chart(ctx, {
		type: 'line',
		data: {
			"labels": ['28일', '1년', '10년'],
			datasets: [
						{
							"label": '내륙환경',
							backgroundColor: 'rgba(54, 162, 235, 1)',
							borderColor: 'rgba(54, 162, 235, 1)',
							borderWidth: 1,
							fill: false,
							lineTension: 0,
							data: [32.6,38.5,42.6]
						},
						{
							label: '해안환경',
							backgroundColor: 'rgba(255, 99, 132, 0.2)',
							borderColor: 'rgba(255, 99, 132, 1)',
							borderWidth: 1,
							fill: false,
							lineTension: 0,
							data: [31.9,38.0,57.2]
						}
			]

		},
		options: {
			title: {
				display: true,
				text: '노출환경에 따른 주기별 압축강도'
			}
		}
	});
	
	
    var ctx2 = document.getElementById('myChart2');
	var myChart2 = new Chart(ctx2, {
	    type: 'bar',
	    data: {
	        labels: ['속초', '인제', '철원', '동두천', '문산/파주', '대관령'],
	        datasets: [{
	            label: '강설횟수',
	            data: [10.20, 10.00, 13.90, 6.00, 4.80, 26.80],
	            backgroundColor: 'rgba(54, 162, 235, 0.2)',
	            borderColor: 'rgba(54, 162, 235, 1)',
	            borderWidth: 1
	        }, {
	        	label: '일반부재(수분미접촉)',
	        	data: [5.00, 13.00, 15.70, 13.40, 13.50, 15.60],
	        	backgroundColor: 'rgba(255, 206, 86, 0.2)',
	        	borderColor: 'rgba(255, 206, 86, 1)',
	        	borderWidth: 1
	        }, {
	        	label: '취약부재(수분접촉)',
	        	data: [44.30, 96.00, 84.00, 78.40, 83.40, 68.50],	        	
	        	backgroundColor: 'rgba(255, 99, 132, 0.2)',
	        	borderColor: 'rgba(255, 99, 132, 1)',
	        	borderWidth: 1
	        }]
	    },
	    options: {
	    	title: {
				display: true,
				text: '동결융해 싸이클'
			},
	        scales: {
	            yAxes: [{
	                ticks: {
	                    beginAtZero: true
	                }
	            }]
	        }
	    }
	});
	
	var ctx3 = document.getElementById('myChart3');
	var myChart3 = new Chart(ctx3, {
		type: 'line',
		data: {
			labels: ['05년 12월', '06년 1월', '06년 2월', '06년 3월', '06년 4월', '06년 5월', '06년 6월'],
			datasets: [{
				label: '5m',
				backgroundColor: 'rgba(54, 162, 235, 1)',
				borderColor: 'rgba(54, 162, 235, 1)',
				borderWidth: 1,
				fill: false,
				lineTension: 0,
				data: [10.3, 11.3, 8.5, 13.3, 18.4, 8.1, 6.2]
			},
			{
				label: '40m',
				backgroundColor: 'rgba(255, 206, 86, 1)',
				borderColor: 'rgba(255, 206, 86, 1)',
				borderWidth: 1,
				fill: false,
				lineTension: 0,
				data: [2.3, 2.6, 3.3, 3.5, 3.8, 2.1, 1.4]
			},
			{
				label: '200m',
				backgroundColor: 'rgba(255, 99, 132, 1)',
				borderColor: 'rgba(255, 99, 132, 1)',
				borderWidth: 1,
				fill: false,
				lineTension: 0,
				data: [1.3, 1.6, 3.2, 1.5, 2.1, 1.0, 0.8]
			},
			{
				label: '500m',
				backgroundColor: 'rgba(75, 192, 192, 1)',
				borderColor: 'rgba(75, 192, 192, 1)',
				borderWidth: 1,
				fill: false,
				lineTension: 0,
				data: [1.3, 1.3, 2.1, 1.3, 1.9, 1.0, 0.7]
			},
			{
				label: '1000m',
				backgroundColor: 'rgba(153, 102, 255, 1)',
				borderColor: 'rgba(153, 102, 255, 1)',
				borderWidth: 1,
				fill: false,
				lineTension: 0,
				data: [0.7, 0.6, 0.7, 0.8, 1.3, 1.9, 1.0, 0.7]
			},
			{
				label: '2000m',
				backgroundColor: 'rgba(255, 159, 64, 1)',
				borderColor: 'rgba(255, 159, 64, 1)',
				borderWidth: 1,
				fill: false,
				lineTension: 0,
				data: [0.5, 0.6, 0.7, 0.7, 1.0, 0.6, 0.5]
			},
			{
				label: '5000m',
				backgroundColor: 'rgba(255, 99, 132, 0.5)',
				borderColor: 'rgba(255, 99, 132, 0.5)',
				borderWidth: 1,
				fill: false,
				lineTension: 0,
				data: [0.5, 0.6, 0.9, 0.9, 1.0, 0.4, 0.3]
			}]
		},
		options: {
			title: {
				display: true,
				text: '해안거리에 따른 비래염분 데이터 예시'
			}
		}
	});
	
	
    var ctx4 = document.getElementById('myChart4');
	var myChart4 = new Chart(ctx4, {
	    type: 'bar',
	    data: {
	        labels: ['속초', '인제', '철원', '동두천', '문산/파주', '대관령'],
	        datasets: [{
	            label: '강설횟수',
	            data: [10.20, 10.00, 13.90, 6.00, 4.80, 26.80],
	            backgroundColor: 'rgba(54, 162, 235, 0.2)',
	            borderColor: 'rgba(54, 162, 235, 1)',
	            borderWidth: 1
	        }, {
	        	label: '일반부재(수분미접촉)',
	        	data: [5.00, 13.00, 15.70, 13.40, 13.50, 15.60],
	        	backgroundColor: 'rgba(255, 206, 86, 0.2)',
	        	borderColor: 'rgba(255, 206, 86, 1)',
	        	borderWidth: 1
	        }, {
	        	label: '취약부재(수분접촉)',
	        	data: [44.30, 96.00, 84.00, 78.40, 83.40, 68.50],	        	
	        	backgroundColor: 'rgba(255, 99, 132, 0.2)',
	        	borderColor: 'rgba(255, 99, 132, 1)',
	        	borderWidth: 1
	        }]
	    },
	    options: {
	    	title: {
				display: true,
				text: '동결융해 싸이클'
			},
	        scales: {
	            yAxes: [{
	                ticks: {
	                    beginAtZero: true
	                }
	            }]
	        }
	    }
	});
	*/

})
