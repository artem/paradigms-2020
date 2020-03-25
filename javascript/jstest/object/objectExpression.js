"use strict";

function Const(value) {
    this.value = value;
}
Const.prototype.evaluate = function () {
    return this.value;
};
Const.prototype.toString = function () {
    return this.value.toString();
};



function Variable(value) {
    this.name = value;
}
Variable.prototype.evaluate = function (x, y, z) {
    switch (this.name) {
        case 'x':
            return x;
        case 'y':
            return y;
        case 'z':
            return z;
    }
};
Variable.prototype.toString = function () {
    return this.name;
};



function Unary(f1) {
    this.f1 = f1;
}
Unary.prototype.evaluate = function(x, y, z) {
    return this.calculate(this.f1.evaluate(x, y, z));
};
Unary.prototype.toString = function() {
    return `${this.f1} ${this.op}`;
};



function Binary(f1, f2) {
    this.f1 = f1;
    this.f2 = f2;
}
Binary.prototype.evaluate = function(x, y, z) {
    return this.calculate(this.f1.evaluate(x, y, z), this.f2.evaluate(x, y, z));
};
Binary.prototype.toString = function() {
    return `${this.f1} ${this.f2} ${this.op}`;
};



const Add = function (f1, f2) {
    Binary.apply(this, arguments);
    this.op = '+';
};
Add.prototype = Object.create(Binary.prototype);
Add.prototype.constructor = Add;
Add.prototype.calculate = (x, y) => x + y;



const Subtract = function (f1, f2) {
    Binary.apply(this, arguments);
    this.op = '-';
};
Subtract.prototype = Object.create(Binary.prototype);
Subtract.prototype.constructor = Subtract;
Subtract.prototype.calculate = (x, y) => x - y;



const Multiply = function (f1, f2) {
    Binary.apply(this, arguments);
    this.op = '*';
};
Multiply.prototype = Object.create(Binary.prototype);
Multiply.prototype.constructor = Multiply;
Multiply.prototype.calculate = (x, y) => x * y;



const Divide = function (f1, f2) {
    Binary.apply(this, arguments);
    this.op = '/';
};
Divide.prototype = Object.create(Binary.prototype);
Divide.prototype.constructor = Divide;
Divide.prototype.calculate = (x, y) => x / y;



const Negate = function (f1) {
    Unary.apply(this, arguments);
    this.op = 'negate';
};
Negate.prototype = Object.create(Unary.prototype);
Negate.prototype.constructor = Negate;
Negate.prototype.calculate = x => -x;