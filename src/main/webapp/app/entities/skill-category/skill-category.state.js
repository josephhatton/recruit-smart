(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('skill-category', {
        parent: 'entity',
        url: '/skill-category',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'SkillCategories'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/skill-category/skill-categories.html',
            controller: 'SkillCategoryController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('skill-category-detail', {
        parent: 'skill-category',
        url: '/skill-category/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'SkillCategory'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/skill-category/skill-category-detail.html',
            controller: 'SkillCategoryDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'SkillCategory', function ($stateParams, SkillCategory) {
            return SkillCategory.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'skill-category',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('skill-category-detail.edit', {
        parent: 'skill-category-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/skill-category/skill-category-dialog.html',
            controller: 'SkillCategoryDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['SkillCategory', function (SkillCategory) {
                return SkillCategory.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('skill-category.new', {
        parent: 'skill-category',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/skill-category/skill-category-dialog.html',
            controller: 'SkillCategoryDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  name: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('skill-category', null, {reload: 'skill-category'});
          }, function () {
            $state.go('skill-category');
          });
        }]
      })
      .state('skill-category.edit', {
        parent: 'skill-category',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/skill-category/skill-category-dialog.html',
            controller: 'SkillCategoryDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['SkillCategory', function (SkillCategory) {
                return SkillCategory.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('skill-category', null, {reload: 'skill-category'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('skill-category.delete', {
        parent: 'skill-category',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/skill-category/skill-category-delete-dialog.html',
            controller: 'SkillCategoryDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['SkillCategory', function (SkillCategory) {
                return SkillCategory.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('skill-category', null, {reload: 'skill-category'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
