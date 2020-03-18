"use strict";

const cnst = value => x => value;
const variable = () => x => x;
const parse = input => input.trim() === "x" ? variable("x") : cnst(+input);

const negate = f1 => x => -f1(x);

const binaryOp = op => (f1, f2) => x => op(f1(x), f2(x));
const add = binaryOp((v1, v2) => v1 + v2);
const subtract = binaryOp((v1, v2) => v1 - v2);
const multiply = binaryOp((v1, v2) => v1 * v2);
const divide = binaryOp((v1, v2) => v1 / v2);
