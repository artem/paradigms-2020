#/bin/bash
set -eu

check_dir() {
    if [[ ! -d "$1" ]] ; then
        echo Directory \"$1\" does not exists
        exit 1
    fi
}

if [[ "${2:-}" == "" ]] ; then
    echo Usage: compile-homework.sh repository-id homework
    exit 1
fi

case "$2" in
    3|4)
        tests=queue
        ;;
    5)
        tests=expression/generic
        ;;
    *)
        echo Unknown homework \"$2\"
        echo Supported homeworks:
        echo "   3 Array queue"
        echo "   4 Queues"
        echo "   5 Generic expressions"
        exit 1
esac

base="$(dirname "${BASH_SOURCE[0]}")"
repo="$base/../../$1"
check_dir "$repo"
check_dir "$repo/java-solutions"
check_dir "$repo/java-solutions/$tests"

build="$base/_build"
[[ -e "$build" ]] || rm -rf "$build"

sources=$(find "$repo/java-solutions/$tests" -name '*.java')

echo javac -d "$build" -cp "$base:$repo/java-solutions" "$base/$tests/*.java" $sources
     javac -d "$build" -cp "$base:$repo/java-solutions" "$base/$tests/*.java" $sources

echo SUCCESS