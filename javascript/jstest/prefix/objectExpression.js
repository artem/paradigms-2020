"use strict";

const expression = (function () {
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
    Variable.prototype.prefix = Variable.prototype.toString;


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
        Const: Const,
        Variable: Variable,
        Add: abstractOp((x, y) => x + y, '+'),
        Subtract: abstractOp((x, y) => x - y, '-'),
        Multiply: abstractOp((x, y) => x * y, '*'),
        Divide: abstractOp((x, y) => x / y, '/'),
        Negate: abstractOp(x => -x, 'negate'),
        Sinh: abstractOp(x => Math.sinh(x), 'sinh'),
        Cosh: abstractOp(x => Math.cosh(x), 'cosh')
    }
})();


const Const = expression.Const;
const Variable = expression.Variable;
const Add = expression.Add;
const Subtract = expression.Subtract;
const Multiply = expression.Multiply;
const Divide = expression.Divide;
const Negate = expression.Negate;
const Sinh = expression.Sinh;
const Cosh = expression.Cosh;


const parser = (function () {
    function ParsingError(msg) {
        this.message = msg;
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
            throw new ParsingError('Expected ' + exp + ', got ' + cur);
        } else {
            this.pos++;
        }
    }

    const VARS = ['x', 'y', 'z'];

    function parseOperation(expr) {
        const OPERATIONS = {
            '+': [Add, 2],
            '-': [Subtract, 2],
            '*': [Multiply, 2],
            '/': [Divide, 2],
            'negate': [Negate, 1],
            'sinh': [Sinh, 1],
            'cosh': [Cosh, 1]
        };

        expr.expect('(');
        expr.skipWhitespace();

        let args = [];
        let opName = expr.nextToken();
        let op = OPERATIONS[opName];

        if (op === undefined) {
            throw new ParsingError('Unknown operation: ' + opName);
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
        expr.skipWhitespace();
        let letter = expr.curLetter();
        let result;
        if (letter >= '0' && letter <= '9' || letter === '-') {
            let oldPos = expr.pos++;
            while (expr.curLetter() >= '0' && expr.curLetter() <= '9') {
                expr.pos++;
            }
            let numStr = expr.src.substring(oldPos, expr.pos);
            let number = parseFloat(numStr);
            if (isNaN(number)) {
                throw new ParsingError('Constant ' + numStr + ' is not a valid value.');
            }
            result = new Const(number);
        } else if (VARS.includes(letter)) { //TODO messy
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
        if (src.curLetter() !== undefined) {
            throw new ParsingError('Trailing characters present');
        }

        return ret;
    }

    return {
        parsePrefix: parsePrefix
    };
})();

const parsePrefix = parser.parsePrefix;
