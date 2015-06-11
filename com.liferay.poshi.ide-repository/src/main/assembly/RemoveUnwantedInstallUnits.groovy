def basedir = project.basedir.canonicalPath
def repositoryDir = basedir + "/target/repository"
def contentJar = repositoryDir  + "/content.jar"
def contentDir = basedir  + "/target/content.jar/"

// Remove hidden feature

println 'Unzipping content.jar'

def ant = new AntBuilder();   // create an antbuilder

ant.unzip( src: contentJar, dest:contentDir,  overwrite:"true" )

println 'Modify content.xml to remove unwanted IUs from Liferay IDE site'

File contentXml =  new File( contentDir, "content.xml" )
def contentXmlText = contentXml.text

def parser = new XmlParser()
parser.setTrimWhitespace( false )
def root = parser.parseText( contentXmlText )

def hiddenCategory = root.units.unit.findAll{ it.'@id'.equals('com.liferay.poshi.ide-repository.hidden.features') }.get( 0 )
hiddenCategory.parent().remove( hiddenCategory )

class MyXmlNodePrinter extends XmlNodePrinter
{
    MyXmlNodePrinter(PrintWriter out)
    {
       super(out)
    }

    void printSimpleItem(Object value)
    {
       value = value.replaceAll("\n", "&#xA;")
       out.print(value)
    }
}

println 'Overwriting content.xml'
def writer = new StringWriter()
def printer = new XmlNodePrinter( new PrintWriter( writer ) )
printer.setPreserveWhitespace( true )
printer.print( root )
def result = writer.toString()

contentXml.text = result

println 'Zipping back customized content.jar'
ant.zip( destFile: contentJar, baseDir:contentDir )
