/*******************************************************************************
 * Copyright (c) 2007 UBC, SPL
 * All rights reserved.
 * 
 * Contributors:
 *     Seonah Lee - initial mplementation
 *******************************************************************************/

package ca.ubc.cs.salee.classdiagram.testutil;

import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;

/**
 * 
 * @author Seonah Lee salee@cs.ubc.ca
 */
public class SourceModel {

	// get common projects
	static public IProject[] getProjects() {
		return ResourcesPlugin.getWorkspace().getRoot().getProjects();
	}
	
	static	public IProject getProject(String sProject) {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(sProject);
	}

	static	public IPath getProjectPath(String sProject) {
		IProject myProject = ResourcesPlugin.getWorkspace().getRoot().getProject(sProject);
		
		return myProject.getFullPath();
	}
	
	static	public IPath getProjectLocation(String sProject) {
		IProject myProject = ResourcesPlugin.getWorkspace().getRoot().getProject(sProject);
		
		return myProject.getLocation();
	}
	
	static public IFolder getFolder(String sProject, String sFolder) {
		IProject myProject = ResourcesPlugin.getWorkspace().getRoot().getProject(sProject);
		return myProject.getFolder(sFolder);
	}

	static public IFile getFile(String sProject, String sFolder, String sFile) {
		IProject myProject = ResourcesPlugin.getWorkspace().getRoot().getProject(sProject);
		IFolder myFolder = myProject.getFolder(sFolder);
		return myFolder.getFile(sFile);
	}
	
	// get java projects
	static public IJavaModel getJavaModel(String sProject) {
		return JavaCore.create(ResourcesPlugin.getWorkspace().getRoot());
	}
	
	static public IJavaProject getJavaProject(String sProject) throws CoreException {
		IProject myProject = ResourcesPlugin.getWorkspace().getRoot().getProject(sProject);
		
		if (!myProject.hasNature("org.eclipse.jdt.core.javanature")) {
			return null;
		}
		return JavaCore.create(myProject);
	}
	
	static public IPackageFragmentRoot[] getJavaPackageRoots(String sProject) throws CoreException {
		IJavaProject myProject = getJavaProject(sProject);
		
		return myProject.getPackageFragmentRoots(); 
	}
	
	static public IPackageFragment[] getJavaPackages(String sProject) throws CoreException {
		IJavaProject myProject = getJavaProject(sProject);
		
		return myProject.getPackageFragments();
//		IPackageFragment[] fragments = javaProject.getPackageFragments();
//		Vector packageNames = new Vector();
//		for (int i = 0; i < fragments.length; i++) {
//			if (fragments[i].getKind() == IPackageFragmentRoot.K_SOURCE) {
//				packageNames.add(fragments[i].getElementName());
//			}
//		}		
	}
	
	static public Vector<IMethod> getJavaMethods(String sProject) throws CoreException {
		Vector<IMethod> vMethods = new Vector<IMethod>() ;
		
		IJavaProject myProject = getJavaProject(sProject);		
		IPackageFragment[] myPackages = myProject.getPackageFragments();
		
		for (IPackageFragment aPackage: myPackages) {
			ICompilationUnit[] myUnits = aPackage.getCompilationUnits();
			
			for (ICompilationUnit aUnit: myUnits) {
				IType[] myTypes = aUnit.getAllTypes();
				
				for (IType aType: myTypes) {
					IMethod[] myMethods = aType.getMethods();
					
					for (IMethod aMethod: myMethods) {
						vMethods.add(aMethod);
					}
				}				
			}			
		}
		return vMethods; 
	}
	
	static public IPackageFragment getJavaPackage(String sProject, String sFolder) {
		IProject myProject = ResourcesPlugin.getWorkspace().getRoot().getProject(sProject);
		IFolder myFolder = myProject.getFolder(sFolder);
		
		return (IPackageFragment) JavaCore.create(myFolder);
	}
	
	static public ICompilationUnit getJavaFile(String sProject, String sFolder, String sFile) {
		IProject myProject = ResourcesPlugin.getWorkspace().getRoot().getProject(sProject);
		IFolder myFolder = myProject.getFolder(sFolder);
		IFile myFile = myFolder.getFile(sFile);
		
		return (ICompilationUnit) JavaCore.create(myFile);
	}
	
	static public void addChangeListener() {
		IElementChangedListener myElement = new IElementChangedListener() {
			public void elementChanged(ElementChangedEvent event) {
				IJavaElementDelta jed = event.getDelta();
				System.out.println(jed);
				// jed.getElement();
				// getAddedChildren(), getAffectedChildren();
			}
		};
		JavaCore.addElementChangedListener(myElement);
	}
	
//	static void showMethod(IMember member) throws PartInitException, JavaModelException {
//        ICompilationUnit cu = member.getCompilationUnit();
//        IEditorPart javaEditor = JavaUI.openInEditor(cu);
//        JavaUI.revealInEditor(javaEditor, (IJavaElement) member);
//    }
//
//	static void showField(IField field) throws PartInitException, JavaModelException {
//        ICompilationUnit cu = field.getCompilationUnit();
//        IEditorPart javaEditor = JavaUI.openInEditor(cu);
//        JavaUI.revealInEditor(javaEditor, (IJavaElement) field);
//    }
}






