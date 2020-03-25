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


function OpImpl() {
    this.args = [].slice.call(arguments);
}

OpImpl.prototype.evaluate = function () {
    return this.calculate(...(this.args.map(val => val.evaluate(...arguments))));
};
OpImpl.prototype.toString = function () {
    return this.args.join(' ') + ' ' + this.op;
};


function abstractOp(calc, op) {
    let Operation = function () {
        OpImpl.apply(this, arguments);
        this.op = op;
    };
    Operation.prototype = Object.create(OpImpl.prototype);
    Operation.prototype.constructor = Operation;
    Operation.prototype.calculate = calc;
    return Operation;
}


const Add = abstractOp((x, y) => x + y, '+');
const Subtract = abstractOp((x, y) => x - y, '-');
const Multiply = abstractOp((x, y) => x * y, '*');
const Divide = abstractOp((x, y) => x / y, '/');
const Negate = abstractOp(x => -x, 'negate');
