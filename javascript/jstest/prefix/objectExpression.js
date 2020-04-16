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
Const.prototype.prefix = Const.prototype.toString;


function Variable(value) {
    this.name = value;
}

Variable.prototype.evaluate = function (...values) {
    switch (this.name) {
        case 'x':
            return values[0];
        case 'y':
            return values[1];
        case 'z':
            return values[2];
    }
};
Variable.prototype.toString = function () {
    return this.name;
};
Variable.prototype.prefix = Variable.prototype.toString;


const operations = (function () {
    function OpImpl() {
        this.args = [].slice.call(arguments);
    }

    OpImpl.prototype.evaluate = function () {
        return this.calculate(...(this.args.map(val => val.evaluate(...arguments))));
    };
    OpImpl.prototype.toString = function () {
        return '(' + this.op + ' ' + this.args.join(' ') + ')';
    };
    OpImpl.prototype.prefix = OpImpl.prototype.toString;


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


    return {
        Add: abstractOp((x, y) => x + y, '+'),
        Subtract: abstractOp((x, y) => x - y, '-'),
        Multiply: abstractOp((x, y) => x * y, '*'),
        Divide: abstractOp((x, y) => x / y, '/'),
        Negate: abstractOp(x => -x, 'negate'),
        Sinh: abstractOp(x => Math.sinh(x), 'sinh'),
        Cosh: abstractOp(x => Math.cosh(x), 'cosh')
    }
})();


const Add = operations.Add;
const Subtract = operations.Subtract;
const Multiply = operations.Multiply;
const Divide = operations.Divide;
const Negate = operations.Negate;
const Sinh = operations.Sinh;
const Cosh = operations.Cosh;

function ParsingError(expr, msg) {
    this.message = 'col ' + (expr.pos + 1) + ': ' + msg;
}

ParsingError.prototype = Object.create(Error.prototype);

function StringSource(str) {
    this.src = str;
    this.pos = 0;
}

StringSource.prototype.curLetter = function () {
    return this.src[this.pos];
}
StringSource.prototype.skipWhitespace = function () {
    while (this.curLetter() === ' ') {
        this.pos++;
    }
}
StringSource.prototype.nextToken = function () {
    const SEPARATORS = [undefined, ' ', '(', ')'];
    this.skipWhitespace();

    let oldPos = this.pos;
    while (!SEPARATORS.includes(this.curLetter())) {
        this.pos++;
    }
    return this.src.substring(oldPos, this.pos);
}
StringSource.prototype.expect = function (exp) {
    let cur = this.curLetter();

    if (cur !== exp) {
        throw new ParsingError(this,
            'Expected ' + (exp === undefined ? 'end of expression' : '\'' + exp + '\'') + ', got ' + (cur === undefined ? 'end of expression' : '\'' + cur + '\''));
    }
    this.pos++;
}

const parser = (function () {
    function parseOperation(expr) {
        const OPERATIONS = {
            '+': [Add, 2],
            '-': [Subtract, 2],
            '*': [Multiply, 2],
            '/': [Divide, 2],
            negate: [Negate, 1],
            sinh: [Sinh, 1],
            cosh: [Cosh, 1]
        };

        expr.expect('(');
        expr.skipWhitespace();
        let args = [];
        let opName = expr.nextToken();
        let op = OPERATIONS[opName];
        if (op === undefined) {
            throw new ParsingError(expr, 'Unknown operation: \'' + opName + '\'');
        }
        for (let i = 0; i < op[1]; i++) {
            expr.skipWhitespace();
            args.push(parseValue(expr));
        }
        expr.skipWhitespace();
        expr.expect(')');
        return new op[0](...args);
    }

    function parseValue(expr) {
        const VARS = ['x', 'y', 'z'];

        let result;
        expr.skipWhitespace();
        let letter = expr.curLetter();
        if (letter >= '0' && letter <= '9' || letter === '-') {
            let oldPos = expr.pos++;
            while (expr.curLetter() >= '0' && expr.curLetter() <= '9') {
                expr.pos++;
            }
            let numStr = expr.src.substring(oldPos, expr.pos);
            let number = parseFloat(numStr);
            if (isNaN(number)) {
                throw new ParsingError(expr, 'Constant \'' + numStr + '\' is not a valid value.');
            }
            result = new Const(number);
        } else if (VARS.includes(letter)) {
            result = new Variable(letter);
            expr.pos++;
        } else {
            result = parseOperation(expr);
        }
        return result;
    }

    function parsePrefix(expr) {
        let src = new StringSource(expr);
        let ret = parseValue(src);

        src.skipWhitespace();
        src.expect(undefined);
        return ret;
    }

    return {parsePrefix: parsePrefix};
})();

const parsePrefix = parser.parsePrefix;
