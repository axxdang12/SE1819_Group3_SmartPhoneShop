        document.addEventListener('DOMContentLoaded', function() {
            var ctxLine = document.getElementById('lineChart').getContext('2d');
            var ctxBar = document.getElementById('barChart').getContext('2d');
            var ctxPie = document.getElementById('pieChart').getContext('2d');

            var lineChart = new Chart(ctxLine, {
                type: 'line',
                data: {
                    labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
                    datasets: [{
                        label: 'Revenue',
                        data: [12, 19, 3, 5, 2, 3, 7],
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });

            var barChart = new Chart(ctxBar, {
                type: 'bar',
                data: {
                    labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
                    datasets: [{
                        label: 'Sales',
                        data: [10, 20, 30, 40, 50, 60, 70],
                        backgroundColor: 'rgba(153, 102, 255, 0.2)',
                        borderColor: 'rgba(153, 102, 255, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });

            var pieChart = new Chart(ctxPie, {
                type: 'pie',
                data: {
                    labels: ['Red', 'Blue', 'Yellow'],
                    datasets: [{
                        label: 'My First Dataset',
                        data: [300, 50, 100],
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)'
                        ],
                        borderWidth: 1
                    }]
                }
            });
        });


       $(function () {
           Chart.defaults.font.color = 'white'; // Với Chart.js 3.x trở lên

           loadChartData(); // Load data and draw charts

           $(window).resize(function () {
               updateLineChart();
               updateBarChart();
           });
       });

       function loadChartData() {
           $.ajax({
               url: '/api/orders/all',
               method: 'GET',
               dataType: 'json',
               success: function(data) {
                   drawLineChart(data);
                   drawBarChart(data);
                   drawPieChart(data);
               },
               error: function(xhr, status, error) {
                   console.error('Error loading chart data:', {
                       status: status,
                       error: error,
                       responseText: xhr.responseText
                   });
               }
           });
       }

      function drawLineChart(data) {
          let ctxLine = document.getElementById('lineChart').getContext('2d');
          let configLine = {
              type: 'line',
              data: {
                  labels: data.map(order => order.orderDate),
                  datasets: [{
                      label: 'Total Price',
                      data: data.map(order => order.totalPrice),
                      borderColor: 'rgba(75, 192, 192, 1)',
                      borderWidth: 2, // Điều chỉnh độ rộng của đường vẽ
                      backgroundColor: 'rgba(75, 192, 192, 0.2)', // Màu nền của đường vẽ
                      fill: false // Không tô màu bên dưới đường vẽ
                  }]
              },
              options: {
                  responsive: true, // Tự động điều chỉnh kích thước theo kích thước của container
                  plugins: {
                      legend: {
                          display: true, // Hiển thị chú thích
                          position: 'top' // Vị trí của chú thích
                      },
                      tooltip: {
                          callbacks: {
                              label: function(context) {
                                  return `Total Price: $${context.raw}`;
                              }
                          }
                      }
                  },
                  scales: {
                      x: {
                          title: {
                              display: true,
                              text: 'Order Date' // Tiêu đề cho trục x
                          },
                          ticks: {
                              autoSkip: true, // Tự động bỏ qua một số nhãn để không bị chen chúc
                              maxRotation: 45, // Tối đa góc xoay của nhãn
                              minRotation: 0 // Góc xoay tối thiểu của nhãn
                          }
                      },
                      y: {
                          title: {
                              display: true,
                              text: 'Total Price' // Tiêu đề cho trục y
                          },
                          ticks: {
                              beginAtZero: true // Bắt đầu trục y từ 0
                          }
                      }
                  }
              }
          };
          new Chart(ctxLine, configLine);
      }


       function drawBarChart(data) {
           let ctxBar = document.getElementById('barChart').getContext('2d');
           let configBar = {
               type: 'bar',
               data: {
                   labels: data.map(order => order.orderDate),
                   datasets: [{
                       label: 'Total Price',
                       data: data.map(order => order.totalPrice),
                       backgroundColor: 'rgba(75, 192, 192, 0.2)',
                       borderColor: 'rgba(75, 192, 192, 1)',
                       borderWidth: 1
                   }]
               },
               options: {}
           };
           new Chart(ctxBar, configBar);
       }

       function drawPieChart(data) {
           let ctxPie = document.getElementById('pieChart').getContext('2d');
           let configPie = {
               type: 'pie',
               data: {
                   labels: data.map(order => order.status),
                   datasets: [{
                       label: 'Order Status',
                       data: data.map(order => order.totalPrice),
                       backgroundColor: [
                           'rgba(255, 99, 132, 0.2)',
                           'rgba(54, 162, 235, 0.2)',
                           'rgba(255, 206, 86, 0.2)',
                           'rgba(75, 192, 192, 0.2)',
                           'rgba(153, 102, 255, 0.2)',
                           'rgba(255, 159, 64, 0.2)'
                       ],
                       borderColor: [
                           'rgba(255, 99, 132, 1)',
                           'rgba(54, 162, 235, 1)',
                           'rgba(255, 206, 86, 1)',
                           'rgba(75, 192, 192, 1)',
                           'rgba(153, 102, 255, 1)',
                           'rgba(255, 159, 64, 1)'
                       ],
                       borderWidth: 1
                   }]
               },
               options: {}
           };
           new Chart(ctxPie, configPie);
       }