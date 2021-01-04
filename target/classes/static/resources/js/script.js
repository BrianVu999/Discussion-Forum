function deleteAnn(id) {
	fetch('http://localhost:8080/deleteAnn/' + id)
		.then(data => data.json());
	location.reload;
}

function listUser() {
	fetch('http://localhost:8080/getUserList')
		.then(data => data.json())
		.then(function(data) {
			var userlist = "";
			for (var k in data) {
				userlist+=  data[k].substring(10, data[k].length - 1) + ", ";
			}
			document.getElementById("userListContainer").innerHTML = userlist;
		});
}