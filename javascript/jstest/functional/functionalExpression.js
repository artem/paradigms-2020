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

const parse = input => input.trim() === "x" ? variable("x") : cnst(+input);

const negate = f1 => (x, y, z) => -f1(x, y, z);

const binaryOp = op => (f1, f2) => (x, y, z) => op(f1(x, y, z), f2(x, y, z));
const ternaryOp = op => (f1, f2, f3) => (x, y, z) => op(f1(x, y, z), f2(x, y, z), f3(x, y, z));
const quinaryOp = op => (f1, f2, f3, f4, f5) => (x, y, z) => op(f1(x, y, z), f2(x, y, z), f3(x, y, z), f4(x, y, z), f5(x, y, z));

const add = binaryOp((v1, v2) => v1 + v2);
const subtract = binaryOp((v1, v2) => v1 - v2);
const multiply = binaryOp((v1, v2) => v1 * v2);
const divide = binaryOp((v1, v2) => v1 / v2);

const med3 = ternaryOp((v1, v2, v3) => Math.max(Math.min(v1, v2), Math.min(v1, v3), Math.min(v2, v3)));
const avg5 = quinaryOp((v1, v2, v3, v4, v5) => (v1 + v2 + v3 + v4 + v5) / 5);
