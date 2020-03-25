"use strict";

const cnst = value => () => value;
const pi = cnst(Math.PI);
const e = cnst(Math.E);

const variable = name => {
    return (x, y, z) => {
        switch (name) {
            case 'x':
                return x;
            case 'y':
                return y;
            case 'z':
                return z;
        }
    }
};

const operation = op => (...ops) => (x, y, z) => op(...(ops.map(value => value(x, y, z))));

const negate = operation(v1 => -v1);
const add = operation((v1, v2) => v1 + v2);
const subtract = operation((v1, v2) => v1 - v2);
const multiply = operation((v1, v2) => v1 * v2);
const divide = operation((v1, v2) => v1 / v2);

const avg = (...args) => [].reduce.call(args, (acc, cVal) => acc + cVal) / args.length;
const med = (...args) => {
    if (args.length === 0) {
        return 0;
    }

    [].sort.call(args, (a, b) => a - b);
    let half = Math.floor(args.length / 2);

    if (args.length % 2) {
        return args[half];
    }
    return (args[half - 1] + args[half]) / 2;
};

const med3 = operation((v1, v2, v3) => med(v1, v2, v3));
const avg5 = operation((v1, v2, v3, v4, v5) => avg(v1, v2, v3, v4, v5));


const OPERATIONS = {
    '+': [add, 2],
    '-': [subtract, 2],
    '*': [multiply, 2],
    '/': [divide, 2],
    'negate': [negate, 1],
    'avg5': [avg5, 5],
    'med3': [med3, 3]
};

const VARS = ['x', 'y', 'z'];
const CONSTANTS = {
    'pi': pi,
    'e': e
};

function parse(expr) {
    let stack = [];
    const tokens = expr.match(/\S+/g);
    for (let token of tokens) {
        parseToken(token, stack);
    }
    return stack.pop();
}

const parseToken = (token, stack) => {
    let res;
    const curOperation = OPERATIONS[token];
    if (curOperation === undefined) {
        if (VARS.includes(token)) {
            res = variable(token);
        } else if (token in CONSTANTS) {
            res = CONSTANTS[token];
        } else {
            res = cnst(parseFloat(token));
        }
    } else {
        let args = [];
        for (let i = 0; i < curOperation[1]; i++) {
            args.unshift(stack.pop());
        }
        res = curOperation[0](...args);
    }
    stack.push(res);
};
