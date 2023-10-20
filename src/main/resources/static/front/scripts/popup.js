var popup = document.getElementById('popups');
var imgPopup = document.getElementById('image-popup');
var closeOne = document.getElementById('popupClose');
var claseTwo = document.getElementById('imagePopupClose')

window.onload = function() {
	popup.style.display = "block";
	imgPopup.style.display = 'block';
	// Automatic show popup after 2 seconds of page load
}

closeOne.addEventListener('click', () => {
	popup.style.display = "none";
});

claseTwo.addEventListener('click', () => {
	imgPopup.style.display = "none";
});