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


function abstractOp(type, calc, op) {
    let Operation = function () {
        type.apply(this, arguments);
        this.op = op;
    };
    Operation.prototype = Object.create(type.prototype);
    Operation.prototype.constructor = Operation;
    Operation.prototype.calculate = calc;
    return Operation;
}


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


const Add = abstractOp(Binary, (x, y) => x + y, '+');
const Subtract = abstractOp(Binary, (x, y) => x - y, '-');
const Multiply = abstractOp(Binary, (x, y) => x * y, '*');
const Divide = abstractOp(Binary, (x, y) => x / y, '/');
const Negate = abstractOp(Unary, x => -x, 'negate');
