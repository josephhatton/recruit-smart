(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('SkillCategoryController', SkillCategoryController);

  SkillCategoryController.$inject = ['SkillCategory', 'SkillCategorySearch'];

  function SkillCategoryController(SkillCategory, SkillCategorySearch) {

    var vm = this;

    vm.skillCategories = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      SkillCategory.query(function (result) {
        vm.skillCategories = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      SkillCategorySearch.query({query: vm.searchQuery}, function (result) {
        vm.skillCategories = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
