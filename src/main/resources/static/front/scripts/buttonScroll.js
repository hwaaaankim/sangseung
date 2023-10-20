window.onscroll = function (e) {
  var target = document.getElementById("body");
  var overall = document.getElementById("overallBtn");
  var reason = document.getElementById("reasonBtn");
  var team = document.getElementById("teamBtn");
  var history = document.getElementById("historyBtn");
  var height = target.getBoundingClientRect().y;
  if (height > -1200) {
    overall.classList.add("active");
    reason.classList.remove("active");
    team.classList.remove("active");
    history.classList.remove("active");
  } else if (height < -1200 && height > -3078) {
    overall.classList.remove("active");
    reason.classList.add("active");
    team.classList.remove("active");
    history.classList.remove("active");
  } else if (height < -3078 && height > -5000) {
    overall.classList.remove("active");
    reason.classList.remove("active");
    team.classList.add("active");
    history.classList.remove("active");
  } else if (height < -5000) {
    overall.classList.remove("active");
    reason.classList.remove("active");
    team.classList.remove("active");
    history.classList.add("active");
  }
};
