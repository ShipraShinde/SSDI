<!DOCTYPE html>
<html>
<style>
table, th, td {
	border: 1px solid grey;
	border-collapse: collapse;
	padding: 5px;
}

table tr:nth-child(odd) {
	background-color: #f1f1f1;
}

table tr:nth-child(even) {
	background-color: #ffffff;
}
</style>
<head>
<meta charset="ISO-8859-1">
<title>SSDI Project Setup Demo</title>
<script type="text/javascript" src="js/angular.min.js"></script>
<script>
	var app = angular.module('myApp', []);
	function MyController($scope, $http) {
		$scope.myVar = false;
		$scope.getDataFromServer = function() {
			$http({
				method : 'GET',
				url : 'ssdiSprintDemo'
			}).success(function(data, status, headers, config) {
				$scope.scheduleDetails = angular.fromJson(data);
				console.log($scope.scheduleDetails);
				$scope.myVar = !$scope.myVar;
			}).error(function(data, status, headers, config) {
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});

		};
	};
</script>

</head>
<body>
	<div data-ng-app="myApp">
		<div data-ng-controller="MyController">
			<button data-ng-click="getDataFromServer()">View Student
				Schedule</button>
			<br> <br>
			<table data-ng-table="myTable">
				<tr data-ng-show="myVar">
					<th>Course</th>
					<th>Days</th>
					<th>Time</th>
					<th>Instructor</th>
					<th>Location</th>
				</tr>
				<tr data-ng-show="myVar"
					data-ng-repeat="schedule in scheduleDetails">
					<td>{{schedule.subject}}</td>
					<td>{{schedule.days}}</td>
					<td>{{schedule.time}}</td>
					<td>{{schedule.instructor}}</td>
					<td>{{schedule.location}}</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>