$(document).ready(function() {

	
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

})
