"use strict";

const addImpl = (v1, v2) => v1 + v2;
const subImpl = (v1, v2) => v1 - v2;
const mulImpl = (v1, v2) => v1 * v2;
const divImpl = (v1, v2) => v1 / v2;

const cnst = value => x => value;
const variable = () => x => x;
const parse = input => input.trim() === "x" ? variable("x") : cnst(+input);

const negate = f1 => x => -f1(x);

const binaryOp = op => (f1, f2) => x => op(f1(x), f2(x));
const add = binaryOp(addImpl);
const subtract = binaryOp(subImpl);
const multiply = binaryOp(mulImpl);
const divide = binaryOp(divImpl);
