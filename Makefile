TXT_FILES=	$(shell ls *.txt)
HTML_FILES=	$(patsubst %.txt,%.html,${TXT_FILES})

.SUFFIXES: .txt .html

all: ${HTML_FILES}

.txt.html:
	asciidoc $<

clean:
	rm -f ${HTML_FILES}
