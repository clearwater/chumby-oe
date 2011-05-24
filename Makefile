# On debian: 
# sudo dpkg-reconfigure dash

.PHONY:	build clean
build:
	bitbake $(CHUMBY_IMAGE)

buildlog:
	bitbake $(CHUMBY_IMAGE) 2>&1 > build.log

clean:
	rm -rf $(OUTPUT)

