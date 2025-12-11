const passwordForm = document.getElementById('password-form'); 
const passwordInput = document.getElementById('password'); 
const confirmPasswordInput = document.getElementById('confirm
password'); 
const errorMessage = document.getElementById('error-message'); 
passwordForm.addEventListener('submit', (e) => { 
e.preventDefault(); 
const password = passwordInput.value; 
const confirmPassword = confirmPasswordInput.value; 
if (password.length < 8) { 
errorMessage.textContent = 'Password must be at least 8 characters 
long.'; 
return; 
} 
if (!/[A-Z]/.test(password)) { 
errorMessage.textContent = 'Password must contain at least one 
uppercase letter.'; 
return; 
} 
if (!/[a-z]/.test(password)) { 
errorMessage.textContent = 'Password must contain at least one 
lowercase letter.'; 
return; 
} 
if (!/[0-9]/.test(password)) { 
errorMessage.textContent = 'Password must contain at least one 
number.'; 
return; 
} 
if (password !== confirmPassword) { 
errorMessage.textContent = 'Passwords do not match.'; 
return; 
} 
errorMessage.textContent = ''; 
alert('Redirecting you to our Page'); 
}); 
function op(){ 
var field2=document.getElementById("password").value; 
if(field2==="Vikrant@123") 
window.location="quantum.html"; 
else{ 
alert("invaild information") 
} 
}  