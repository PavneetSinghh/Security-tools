const readline = require('readline');
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

function checkPasswordStrength(password) {
    // Minimum length
    if (password.length < 8) {
        return "Weak: Password length should be at least 8 characters";
    }

    // Checking for uppercase, lowercase, digits, and special characters
    let hasUpper = false;
    let hasLower = false;
    let hasDigit = false;
    let hasSpecial = false;
    const specialCharacters = "!@#$%^&*()-_=+[{]}|;:'\",<.>/?`~";

    for (const ch of password) {
        if (ch.match(/[A-Z]/)) {
            hasUpper = true;
        } else if (ch.match(/[a-z]/)) {
            hasLower = true;
        } else if (ch.match(/[0-9]/)) {
            hasDigit = true;
        } else if (specialCharacters.includes(ch)) {
            hasSpecial = true;
        }
    }

    // Calculating score based on conditions met
    const score = (hasUpper ? 1 : 0) + (hasLower ? 1 : 0) + (hasDigit ? 1 : 0) + (hasSpecial ? 1 : 0);

    // Assigning strength level based on score
    if (score === 4) {
        return "Strong";
    } else if (score >= 2) {
        return "Moderate";
    } else {
        return "Weak: Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character";
    }
}

function suggestImprovement(password) {
    const needsNumber = !password.match(/.*\d.*/);
    const needsSpecial = !password.match(/.*[!@#$%^&()-_=+\[{\]}|;:'",<.>\/?`~].*/);

    let suggestion = "";
    if (needsNumber && needsSpecial) {
        suggestion = "Consider adding numbers and special characters to increase the password strength.";
    } else if (needsNumber) {
        suggestion = "Consider adding numbers to increase the password strength.";
    } else if (needsSpecial) {
        suggestion = "Consider adding special characters to increase the password strength.";
    }

    return suggestion;
}

rl.question("Enter your password: ", function(password) {
    const strength = checkPasswordStrength(password);
    console.log("Password strength: " + strength);
    if (strength === "Weak") {
        const improvementSuggestion = suggestImprovement(password);
        console.log(improvementSuggestion);
    }
    rl.close();
});
