<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>

<h1>test</h1>
<p>${records}

<div id="chart">
</div>



<style>
@import url(https://fonts.googleapis.com/css?family=Roboto);

body {
  font-family: Roboto, sans-serif;
}

#chart {
  max-width: 650px;
  margin: 35px auto;
}

</style>


${categories}
${data}

<input type="hidden" value="[30,40,45,50,49,60,70,91,125]" id="data">
<input type="hidden" value="${categories}" id="categories">

<script>	    


// var test = document.getElementById("data").value;

${test}

		var chart = new ApexCharts(document.querySelector("#chart"), options);

		chart.render();

</script>

















