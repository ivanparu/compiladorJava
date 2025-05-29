# Mini Imperative Language Compiler

Este proyecto implementa un **compilador-intérprete** para un mini-lenguaje imperativo en Java. El lenguaje soporta:

- **Asignaciones**: `x = expr;`
- **Bucles**: `while (cond) { ... }`
- **Condicionales**: `if (cond) { ... }`
- **Operaciones aritméticas**: `+`, `-`, `*`, `/`
- **Comparaciones**: `<`, `>`, `<=`, `>=`, `==`, `!=`
- **Impresión**: `print(expr);`

---

## Cómo funciona el compilador

El compilador-intérprete procesa tu código fuente en cuatro pasos principales:

1. **Análisis Léxico (Lexer)**  
   Convierte el texto en una secuencia de *tokens* (números, identificadores, operadores y símbolos).

2. **Análisis Sintáctico (Parser)**  
   Lee los tokens y construye un **AST** (Abstract Syntax Tree) aplicando reglas recursivo-descendentes.

3. **Construcción del AST**  
   Cada nodo del AST es una instancia de `Expr` o `Stmt`, que modela expresiones y sentencias.

4. **Interpretación (Interpreter)**  
   Se recorre el AST y se ejecuta:
   - Evaluación de expresiones (aritméticas y lógicas).  
   - Gestión de variables en un contexto (`Map<String,Integer>`).  
   - Control de flujo (`if`, `while`).  
   - Impresión de resultados con `print()`.

---

## Estructura del proyecto

```plaintext
compliador/
├─ src/
│  └─ compiler/
│     ├─ TokenType.java
│     ├─ Token.java
│     ├─ Lexer.java
│     ├─ Parser.java
│     ├─ Expr.java
│     ├─ Stmt.java
│     ├─ NumberExpr.java
│     ├─ VarExpr.java
│     ├─ BinOp.java
│     ├─ AssignStmt.java
│     ├─ IfStmt.java
│     ├─ WhileStmt.java
│     ├─ PrintStmt.java
│     └─ Interpreter.java
├─ examples/
│  ├─ count_loop.mini
│  ├─ factorial.mini
│  └─ fibonacci.mini
├─ prog.mini            # Script de prueba por defecto
├─ .gitignore
└─ README.md
```

---

## Requisitos

- **Java JDK 17** o superior instalado y configurado en tu PATH.

---

## Compilación

En la raíz del proyecto (donde esté `src/`), ejecuta los siguientes comandos en la terminal:

```bash
mkdir -p bin
javac -d bin src/compiler/*.java
```

---

## Ejecución

### Ejecutar el script por defecto (`prog.mini`):

```bash
java -cp bin compiler.Interpreter
```

### Ejecutar un ejemplo específico:

```bash
java -cp bin compiler.Interpreter examples/count_loop.mini
```

---

## Ejemplos disponibles

- **`count_loop.mini`**: Imprime números del 1 al 10.
- **`factorial.mini`**: Calcula el factorial de 5 y lo imprime.
- **`fibonacci.mini`**: Imprime la serie de Fibonacci hasta 10 elementos.

---

## Uso de ejemplo

Para ejecutar el ejemplo del cálculo de factorial:

```bash
java -cp bin compiler.Interpreter examples/factorial.mini
```

Salida esperada:

```
120
```