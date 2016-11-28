<?xml version="1.0" encoding="UTF-8"?>
<project name="Criacao do arquivo WAR" basedir="." default="deploywar">

	<!-- definicao das propriedades dos arquivos -->
	<property name="src" value="src" />
	<property name="bin" value="bin" />
	<property name="libs" value="libs" />
	<property name="warfile.name" value="/Users/ramonsantos/bti/workspaces/concorrente_distribuida/EspatifadoServer/exemplo.war" />
	<property name="webappdir" value="web pages" />
	<property name="webxml.file" value="${webappdir}/WEB-INF/web.xml" />
	<property name="webinf.dir" value="${webappdir}/WEB-INF" />
	<property name="deploy.home" value="/home/.../jboss/server/default/deploy" />

	<!-- definindo onde estao os JARs dos sistema -->
	<path id="classpath.base">
		<fileset dir="${libs}">
			<include name="*.jar" />
		</fileset>
	</path>

	<target name="init">
		<echo>Iniciando build web do projeto</echo>
	</target>

	<target name="clean" depends="init">
		<delete dir="${bin}" />
	</target>

	<target name="prepare" depends="init">
		<mkdir dir="${bin}" />
	</target>

	<target name="compile" depends="init, clean, prepare">
		<echo>Compilando classes</echo>
		<javac srcdir="${src}" destdir="${bin}" verbose="false">
			<classpath refid="classpath.base" />
		</javac>
	</target>

	<!-- cria um arquivo war -->
	<target name="criaWar" depends="compile">
		<echo>Gerando arquivo WAR</echo>

		<war warfile="${warfile.name}" webxml="${webxml.file}">
			<fileset dir="${webappdir}">
				<include name="*.html" />
				<include name="*.jsp" />
			</fileset>

			<classes dir="${bin}" />
			<lib dir="${libs}">
				<exclude name="alcuma_jar_desnecessario_ao_modulo_web.jar" />
			</lib>

			<webinf dir="${webinf.dir}">
				<include name="${webxml.file}" />
			</webinf>

		</war>

	</target>

	<!-- faz o deploy no JBoss -->
	<target name="deploywar" depends="criaWar">

		<echo>Deploy do JBOSS</echo>
		<copy file="${warfile.name}" todir="${deploy.home}" />
	</target>

</project>